package com.griddynamics.mybank.service;

import com.griddynamics.mybank.entity.Card;
import com.griddynamics.mybank.entity.Transaction;
import com.griddynamics.mybank.exception.CardIsLockedException;
import com.griddynamics.mybank.repository.CardRepository;
import com.griddynamics.mybank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * @author Sergey Korneev
 */
@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Card lock(String id) {
        Card card = getCard(id);
        card.lock();
        cardRepository.save(card);
        return card;
    }

    @Transactional
    public Card unlock(String id) {
        Card card = getCard(id);
        card.unlock();
        cardRepository.save(card);
        return card;
    }

    @Transactional
    public Card increaseBalance(String id, double summ) {
        Card card = getCard(id);
        if (card.isLocked()) {
            throw new CardIsLockedException();
        }
        card.setBalance(card.getBalance() + summ);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setSumm(summ);
        transaction.setAdminName(auth.getName());
        card.addTransaction(transaction);
        transactionRepository.save(transaction);
        cardRepository.save(card);

        return card;
    }

    public Card getCard(String id) {
        Card card = cardRepository.findOne(id);
        if (card == null) {
            throw new EntityNotFoundException();
        }
        return card;
    }

    public Card[] getCards() {
        return cardRepository.findAll();
    }
}
