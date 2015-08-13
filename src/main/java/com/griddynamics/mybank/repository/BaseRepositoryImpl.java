package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.BaseEntity;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.MappedSuperclass;

/**
 * @author Sergey Korneev
 */
@MappedSuperclass
public class BaseRepositoryImpl implements BaseRepository {

    @Autowired
    GigaSpace gs;

    @Override
    public <T extends BaseEntity> void save(T entity) {
        gs.write(entity);
    }
}
