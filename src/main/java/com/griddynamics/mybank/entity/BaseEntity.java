package com.griddynamics.mybank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author Sergey Korneev
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private String id;

    private Integer routingProperty;

    public void setId(String id) {
        this.id = id;
    }

    @Id
    @SpaceId(autoGenerate = true)
    @XmlElement
    public String getId() {
        return id;
    }

    @SpaceExclude
    @Transient
    @JsonIgnore
    public boolean isNewEntity() {
        return (getId() == null);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o.getClass().equals(this.getClass()))) return false;
        BaseEntity that = (BaseEntity) o;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId()!= null) return false;
        return true;
    }

    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    @SpaceRouting
    @JsonIgnore
    public Integer getRoutingProperty() {
        return routingProperty;
    }

    public void setRoutingProperty(Integer routingProperty) {
        this.routingProperty = routingProperty;
    }
}
