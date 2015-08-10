package com.griddynamics.mybank.entity;

import com.gigaspaces.annotation.pojo.SpaceExclude;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sergey Korneev
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Card extends BaseEntity {
    @XmlElement
    private double balance;

    @XmlElement
    private boolean locked = false;

    @XmlElement(type = Owner.class)
    private Owner owner;

    @XmlElementWrapper(name="transactions")
    @XmlElement(name="transaction")
    private Set<Transaction> transactions;

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

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    protected void setTransactionsInternal(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    protected Set<Transaction> getTransactionsInternal() {
        if (this.transactions == null) {
            this.transactions = new HashSet<>();
        }
        return this.transactions;
    }

    @SpaceExclude
    @Transient
    public Set<Transaction> getTransactions() {
        return Collections.unmodifiableSet(getTransactionsInternal());
    }

    public void addTransaction(Transaction transaction) {
        transaction.setCard(this);
        this.getTransactionsInternal().add(transaction);
    }
}
