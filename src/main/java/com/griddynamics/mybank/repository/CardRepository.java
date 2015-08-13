package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Card;

/**
 * @author Sergey Korneev
 */
public interface CardRepository extends BaseRepository {
    public Card findOne(String id);
    public Card[] findAll();
}
