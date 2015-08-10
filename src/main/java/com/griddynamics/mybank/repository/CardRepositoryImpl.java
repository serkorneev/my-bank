package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Card;
import com.j_spaces.core.client.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Korneev
 */
@Repository
public class CardRepositoryImpl extends BaseRepositoryImpl implements CardRepository {
    @Override
    @Transactional
    public Card findOne(int id) {
        return gs.readById(Card.class, id);
    }

    @Override
    @Transactional
    public Card[] findAll() {
        return gs.readMultiple(new SQLQuery<Card>(Card.class, null));
    }
}
