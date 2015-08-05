package com.griddynamics.mybank.entity;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.metadata.StorageType;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Collections;
import java.util.Set;

/**
 * @author Sergey Korneev
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@SpaceClass(storageType = StorageType.BINARY)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement
    private int id;

    @XmlElement
    private double balance;

    @XmlElement
    private boolean locked = false;

    @ManyToOne
    @XmlElement(type = Owner.class)
    private Owner owner;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @XmlElementWrapper(name="transactions")
    @XmlElement(name="transaction")
    private Set<Transaction> transactions;

    @SpaceId
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<Transaction> getTransactions() {
        return Collections.unmodifiableSet(transactions);
    }

    public void addTransaction(Transaction transaction) {
        transaction.setCard(this);
        this.transactions.add(transaction);
    }
}
