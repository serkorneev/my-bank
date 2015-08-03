package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Card;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Sergey Korneev
 */
public interface CardRepository extends CrudRepository<Card, Integer> {
}
