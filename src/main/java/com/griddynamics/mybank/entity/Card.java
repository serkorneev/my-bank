package com.griddynamics.mybank.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sergey Korneev
 */
@Entity
@Table(name = "card")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double balance;

    private boolean locked = false;

    @ManyToOne
    @XmlElement(type = Owner.class)
    private Owner owner;

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        this.locked = true;
    }

    public void unLock() {
        this.locked = false;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
