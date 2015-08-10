package com.griddynamics.mybank.init;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

/**
 * @author Sergey Korneev
 */
@SpaceClass
public class IdCounterEntry {
    private Integer currentId = 0;

    public Integer getCurrentId() {
        return currentId;
    }

    protected void setCurrentId(Integer currentId) {
        this.currentId = currentId;
    }

    public int generateNextId() {
        return ++currentId;
    }

    @SpaceId
    @SpaceRouting
    protected Integer getRouting() {
        return 0;
    }
    protected void setRouting(Integer routing) {}
}
