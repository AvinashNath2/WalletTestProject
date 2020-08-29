package com.example.ewallet.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="transaction")
public class UserTransaction {

    @Id
    @GeneratedValue
    private Long id;
//    @NotNull
    private BigDecimal amount;

    private Long transactionFrom;

    private Long transactionTo;

    private String details;

    private BigDecimal commissionAmount;

    private BigDecimal chargeAmount;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date transactionDate;
//    @NotNull
    private String transactionHash;

    public UserTransaction() {
    }

    public UserTransaction(Long id, @NotNull BigDecimal amount, Long transactionFrom, Long transactionTo, String details, BigDecimal commissionAmount, BigDecimal chargeAmount, Date transactionDate, @NotNull String transactionHash) {
        this.id = id;
        this.amount = amount;
        this.transactionFrom = transactionFrom;
        this.transactionTo = transactionTo;
        this.details = details;
        this.commissionAmount = commissionAmount;
        this.chargeAmount = chargeAmount;
        this.transactionDate = transactionDate;
        this.transactionHash = transactionHash;
    }

    /** Builder : for Transaction */

    public UserTransaction(TransactionBuilder builder) {
        id = builder.id;
        amount = builder.amount;
        details = builder.details;
        transactionDate = builder.transactionDate;
        commissionAmount = builder.commissionAmount;
        transactionFrom = transactionFrom;
        transactionTo = transactionTo;
        chargeAmount = builder.chargeAmount;
        transactionDate = builder.transactionDate;
        transactionHash = builder.transactionHash;
    }



    public Long getId() {
        return id;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Long getTransactionFrom() {
        return transactionFrom;
    }

    public void setTransactionFrom(Long transactionFrom) {
        this.transactionFrom = transactionFrom;
    }

    public Long getTransactionTo() {
        return transactionTo;
    }

    public void setTransactionTo(Long transactionTo) {
        this.transactionTo = transactionTo;
    }

    public void setId(Long id) {
        this.id = id;
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
        private BigDecimal amount;
        private String details;
        private Date transactionDate;
        private BigDecimal commissionAmount;
        private BigDecimal chargeAmount;
        private String transactionHash;
        private Long transactionFrom;
        private Long transactionTo;

        public TransactionBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public void setTransactionFrom(Long transactionFrom) {
            this.transactionFrom = transactionFrom;
        }

        public void setTransactionTo(Long transactionTo) {
            this.transactionTo = transactionTo;
        }

        public void setTransactionDate(Date transactionDate) {
            this.transactionDate = transactionDate;
        }

        public void setTransactionHash(String transactionHash) {
            this.transactionHash = transactionHash;
        }

        public void setCommissionAmount(BigDecimal commissionAmount) {
            this.commissionAmount = commissionAmount;
        }

        public void setChargeAmount(BigDecimal chargeAmount) {
            this.chargeAmount = chargeAmount;
        }

        public TransactionBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder setDetails(String details) {
            this.details = details;
            return this;
        }

        public UserTransaction build() {
            return new UserTransaction(this);
        }

    }

}