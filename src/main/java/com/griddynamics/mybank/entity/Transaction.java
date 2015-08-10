package com.griddynamics.mybank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Sergey Korneev
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Transaction extends BaseEntity {

    @JsonIgnore
    private Card card;

    @XmlElement
    private double summ;

    @XmlElement
    private String adminName;

    @XmlElement
    private Date creationDate = new Date();

    @ManyToOne
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getSumm() {
        return summ;
    }

    public void setSumm(double summ) {
        this.summ = summ;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {}
}
