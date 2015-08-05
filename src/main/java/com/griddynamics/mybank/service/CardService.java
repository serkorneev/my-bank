package com.griddynamics.mybank.service;

import com.griddynamics.mybank.entity.Card;
import com.griddynamics.mybank.entity.Owner;
import com.griddynamics.mybank.entity.Transaction;
import com.griddynamics.mybank.exception.CardIsLockedException;
import com.griddynamics.mybank.repository.CardRepository;
import com.griddynamics.mybank.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Sergey Korneev
 */
@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @PostConstruct
    public void init() {
        Card card = new Card();
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        ownerRepository.save(owner);
        card.setOwner(owner);
        cardRepository.save(card);
    }

    @Transactional
    public Card lock(int id) {
        Card card = getCard(id);
        card.lock();
        cardRepository.save(card);
        return card;
    }

    @Transactional
    public Card unlock(int id) {
        Card card = getCard(id);
        card.unLock();
        cardRepository.save(card);
        return card;
    }

    @Transactional
    public Card increaseBalance(int id, double summ) {
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
        cardRepository.save(card);

        return getCard(id);
    }

    public Card getCard(int id) {
        Card card = cardRepository.findOne(id);
        if (card == null) {
            throw new EntityNotFoundException();
        }
        return card;
    }

    public List<Card> getCards() {
        return (List<Card>) cardRepository.findAll();
    }
}
