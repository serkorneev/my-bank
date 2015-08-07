package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergey Korneev
 */
@Repository
public class CardRepositoryImpl implements CardRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Card card) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        if (0 != card.getId()) {

            session.update(card);
        } else {
            session.save(card);
        }//persist(card);
        tx.commit();
        session.close();
//        sessionFactory.getCurrentSession().save(card);
    }

    @Override
    public Card findOne(int id) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Card c = (Card) session.get(Card.class, id);
        tx.commit();
        session.close();

        return c;
//        return (Card) sessionFactory.getCurrentSession().get(Card.class, id);
    }

    @Override
    public List<Card> findAll() {
//        Session session = this.sessionFactory.openSession();
//        List<Card> list = session.createQuery("from " + Card.class).list();
//        session.close();
//        return list;
        return sessionFactory.getCurrentSession().createQuery("from " + Card.class).list();
    }
}
