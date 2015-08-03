package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Owner;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Sergey Korneev
 */
public interface OwnerRepository extends CrudRepository<Owner, Integer> {
}
