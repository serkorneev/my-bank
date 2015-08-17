package com.griddynamics.mybank.service;

import com.griddynamics.mybank.entity.Card;
import com.griddynamics.mybank.entity.Transaction;
import com.griddynamics.mybank.exception.CardIsLockedException;
import com.griddynamics.mybank.repository.CardRepository;
import com.griddynamics.mybank.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityNotFoundException;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Sergey Korneev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CardServiceTest {

    @Configuration
    static class Config {
        @Bean
        public TransactionRepository transactionRepository() {
            return mock(TransactionRepository.class);
        }

        @Bean
        public CardRepository cardRepository() {
            return mock(CardRepository.class);
        }

        @Bean
        public CardService cardService() {
            return new CardService();
        }
    }

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardService cardService;

    @Before
    public void clearSpace() {
        reset(transactionRepository);
        reset(cardRepository);
    }

    @Test
    public void getCard() throws Exception {
        String id = "id";

        Card expectedCard = new Card();
        when(cardRepository.findOne(id)).thenReturn(expectedCard);

        Card actualCard = cardService.getCard(id);

        assertEquals(expectedCard, actualCard);
        verify(cardRepository).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getNotExistCard() throws Exception {
        String id = "id";
        when(cardRepository.findOne(id)).thenReturn(null);

        cardService.getCard(id);

        verify(cardRepository).findOne(id);
    }

    @Test
    public void getCards() throws Exception {
        Card[] expected = {new Card()};
        when(cardRepository.findAll()).thenReturn(expected);

        Card[] actual = cardService.getCards();

        assertEquals(expected, actual);
        verify(cardRepository).findAll();
    }

    @Test
    public void lock() throws Exception {
        String id = "id";

        Card expectedCard = new Card();
        expectedCard.unlock();
        when(cardRepository.findOne(id)).thenReturn(expectedCard);
        assertFalse(expectedCard.isLocked());

        Card actualCard = cardService.lock(id);

        assertEquals(expectedCard, actualCard);
        assertTrue(actualCard.isLocked());
        verify(cardRepository).findOne(id);
        verify(cardRepository).save(expectedCard);
    }

    @Test
    public void unlock() throws Exception {
        String id = "id";

        Card expectedCard = new Card();
        expectedCard.lock();
        when(cardRepository.findOne(id)).thenReturn(expectedCard);
        assertTrue(expectedCard.isLocked());

        Card actualCard = cardService.unlock(id);

        assertEquals(expectedCard, actualCard);
        assertFalse(actualCard.isLocked());
        verify(cardRepository).findOne(id);
        verify(cardRepository).save(expectedCard);
    }

    @Test
    public void increaseBalance() throws Exception {
        String id = "id";
        Double summ = 100.0;

        Authentication authentication = new TestingAuthenticationToken("test", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Card expectedCard = new Card();
        when(cardRepository.findOne(id)).thenReturn(expectedCard);
        assertEquals(0.0, expectedCard.getBalance());

        Card actualCard = cardService.increaseBalance(id, summ);

        assertEquals(expectedCard, actualCard);
        assertEquals(summ, actualCard.getBalance());
        assertEquals(1, actualCard.getTransactions().size());
        Transaction transaction = (Transaction) actualCard.getTransactions().toArray()[0];
        assertEquals(summ, transaction.getSumm());
        assertEquals("test", transaction.getAdminName());
        verify(cardRepository).findOne(id);
        verify(cardRepository).save(expectedCard);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test(expected = CardIsLockedException.class)
    public void increaseBalanceForLockingCard() throws Exception {
        String id = "id";
        Card expectedCard = new Card();
        expectedCard.lock();
        when(cardRepository.findOne(id)).thenReturn(expectedCard);

        cardService.increaseBalance(id, 1);

        verify(cardRepository).findOne(id);
        verify(cardRepository, never()).save(any(Card.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
