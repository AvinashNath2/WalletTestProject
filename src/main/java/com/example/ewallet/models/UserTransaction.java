package com.example.ewallet.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
public class UserTransaction {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @NotNull
    private BigDecimal amount;

    private String details;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date transactionDate;
    @NotNull
    private String transactionHash;

    public UserTransaction() {
    }

    public UserTransaction(User user, BigDecimal amount, String details, Date transactionDate,
                           String transactionHash) {
        this.user = user;
        this.amount = amount;
        this.details = details;
        this.transactionDate = transactionDate;
        this.transactionHash = transactionHash;
    }

    /** Builder : for Transaction */

    public UserTransaction(TransactionBuilder builder) {
        id = builder.id;
        user = new User(builder.userAccountId);
        amount = builder.amount;
        details = builder.details;
        transactionDate = builder.transactionDate;
        transactionHash = builder.transactionReference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserAccount() {
        return user;
    }

    public void setUserAccount(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionReference) {
        this.transactionHash = transactionReference;
    }

    public static class TransactionBuilder {

        private Long id;
        private Long userAccountId;
        private BigDecimal amount;
        private String details;
        private Date transactionDate;
        private String transactionReference;

        public TransactionBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder setUserAccount(Long userAccountId) {
            this.userAccountId = userAccountId;
            return this;
        }

        public TransactionBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder setDetails(String details) {
            this.details = details;
            return this;
        }

        public TransactionBuilder setTransactionDate(Date transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public UserTransaction build() {
            return new UserTransaction(this);
        }

    }

}