package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Owner;

/**
 * @author Sergey Korneev
 */
//public interface OwnerRepository extends CrudRepository<Owner, Integer> {
public interface OwnerRepository {
    public void save(Owner owner);
}
