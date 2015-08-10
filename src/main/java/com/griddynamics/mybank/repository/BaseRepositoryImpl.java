package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.BaseEntity;
import com.griddynamics.mybank.init.IdCounterEntry;
import com.j_spaces.core.client.SQLQuery;
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
        if (entity.isNewEntity()) {
            IdCounterEntry idCounterEntry = gs.read(new SQLQuery<IdCounterEntry>(IdCounterEntry.class, null));
            entity.setId(idCounterEntry.generateNextId());
            gs.write(idCounterEntry);
        }
        gs.write(entity);
    }
}
