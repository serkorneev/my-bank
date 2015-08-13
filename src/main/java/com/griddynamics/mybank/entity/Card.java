package com.griddynamics.mybank.entity;

import com.gigaspaces.annotation.pojo.SpaceExclude;

import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;
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

    private String encodedData = createEncodedData();

    private String getEncodedData() {
        return encodedData;
    }

    private void setEncodedData(String data) {
        if (null == data || data.isEmpty()) {
            data = createEncodedData();
        }
        String decodedData = new String(DatatypeConverter.parseBase64Binary(data));
        if (decodedData.matches(".*\\:.*")) {
            String[] decodedInformation = decodedData.split(":");
            this.balance = Double.parseDouble(decodedInformation[0]);
            this.locked = Boolean.parseBoolean(decodedInformation[1]);
            this.encodedData = createEncodedData();
        }
    }

    @SpaceExclude
    @Transient
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
        this.encodedData = createEncodedData();
    }

    @SpaceExclude
    @Transient
    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        setLocked(true);
    }

    public void unlock() {
        setLocked(false);
    }

    private void setLocked(boolean locked) {
        this.locked = locked;
        this.encodedData = createEncodedData();
    }

    private String createEncodedData() {
        String data = balance + ":" + locked;
        return DatatypeConverter.printBase64Binary(data.getBytes());
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "card")
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
