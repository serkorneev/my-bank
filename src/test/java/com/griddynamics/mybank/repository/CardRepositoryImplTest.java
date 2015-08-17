package com.griddynamics.mybank.repository;

import com.griddynamics.mybank.entity.Card;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * @author Sergey Korneev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/context/gs-context.xml", "/context/cardRepository-context.xml"})
public class CardRepositoryImplTest {
    @Autowired
    private CardRepository repository;

    @Autowired
    private GigaSpace space;

    @Before
    public void clearSpace() {
        space.clear(null);
    }

    @Test
    public void findOne() throws Exception {
        String id = "id";
        Card expectedEntity = new Card();
        expectedEntity.setId(id);
        space.write(expectedEntity);

        Card actualEntity = repository.findOne(id);

        assertNotNull(actualEntity);
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    public void findAll() throws Exception {
        String id = "id";
        Card expectedEntity = new Card();
        expectedEntity.setId(id);
        space.write(expectedEntity);

        Card[] actualEntities = repository.findAll();

        assertEquals(1, actualEntities.length);
        assertEquals(expectedEntity, actualEntities[0]);
    }
}
