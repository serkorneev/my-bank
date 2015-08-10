package com.griddynamics.mybank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author Sergey Korneev
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @SpaceId(autoGenerate = false)
    @SpaceRouting
    @XmlElement
    public Integer getId() {
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
}
