package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Card;

import java.util.List;

/**
 * @author Sergey Korneev
 */
//public interface CardRepository extends CrudRepository<Card, Integer> {
public interface CardRepository {
    public void save(Card card);
    public Card findOne(int id);
    public List<Card> findAll();
}
