package com.griddynamics.mybank.init;

import com.griddynamics.mybank.entity.Card;
import com.griddynamics.mybank.entity.Owner;
import com.griddynamics.mybank.repository.CardRepository;
import com.griddynamics.mybank.repository.OwnerRepository;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author Sergey Korneev
 */
@Service
public class ObjectInitializer {
    @Autowired
    private GigaSpace space;

    @ClusterInfoContext
    private ClusterInfo clusterInfo;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @PostConstruct
    @Transactional("jiniTransactionManager")
    public void init() {
        if (shouldWriteObjectToSpace()) {
            IdCounterEntry existingEntry = space.readById(IdCounterEntry.class, 0);
            if (existingEntry == null) {
                existingEntry = new IdCounterEntry();
                space.write(existingEntry);

                Owner owner = new Owner();
                owner.setFirstName("John");
                owner.setLastName("Doe");
                ownerRepository.save(owner);

                Card card = new Card();
                card.setOwner(owner);
                cardRepository.save(card);
            }
        }
    }

    private boolean shouldWriteObjectToSpace() {
        if  (clusterInfo == null)
            return true;
        if (clusterInfo.getInstanceId() == null)
            return true;

        if (clusterInfo.getBackupId() != null )
            return false;

        if (clusterInfo.getInstanceId() == 1)
            return true;
        return false;
    }
}
