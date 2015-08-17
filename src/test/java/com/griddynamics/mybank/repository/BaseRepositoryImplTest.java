package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.BaseEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.*;

/**
 * @author Sergey Korneev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/context/gs-context.xml", "/context/baseRepository-context.xml"})
public class BaseRepositoryImplTest {
    public static class BaseRepositoryStub extends BaseRepositoryImpl {}
    public static class BaseEntityStub extends BaseEntity {}

    @Autowired
    private BaseRepository repository;

    @Autowired
    private GigaSpace space;

    @Before
    public void clearSpace() {
        space.clear(null);
    }

    @Test
    public void save() throws Exception {
        BaseEntity entity = new BaseEntityStub();
        assertNull(space.read(entity));

        repository.save(entity);

        assertNotNull(space.read(entity));
    }
}
