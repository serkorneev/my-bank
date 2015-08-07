package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Owner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Korneev
 */
@Repository
public class OwnerRepositoryImpl implements OwnerRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Owner owner) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(owner);
        tx.commit();
        session.close();
//        sessionFactory.getCurrentSession().save(owner);
    }
}
