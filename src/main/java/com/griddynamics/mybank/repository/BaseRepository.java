package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.BaseEntity;

/**
 * @author Sergey Korneev
 */
public interface BaseRepository {
    public <T extends BaseEntity> void save(T entity);
}
