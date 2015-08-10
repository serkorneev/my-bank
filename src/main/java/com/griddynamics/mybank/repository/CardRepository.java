package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Card;

/**
 * @author Sergey Korneev
 */
public interface CardRepository extends BaseRepository {
    public Card findOne(int id);
    public Card[] findAll();
}
