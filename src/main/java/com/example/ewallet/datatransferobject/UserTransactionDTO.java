package com.example.ewallet.datatransferobject;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/** Client Facing Model of Transaction **/
public class UserTransactionDTO {

	@ApiModelProperty(required = false, hidden = true)
	private Long id;

	private BigDecimal amount;
	private String details;
	private Date transactionDate;
	private Long transactionHash;

	public UserTransactionDTO() {
	}

	public UserTransactionDTO(TransactionDTOBuilder builder) {
		id = builder.id;
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

	public Long getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(Long transactionHash) {
		this.transactionHash = transactionHash;
	}

	public static class TransactionDTOBuilder {

		private Long id;
		private BigDecimal amount;
		private String details;
		private Date transactionDate;
		private Long transactionReference;

		public TransactionDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public TransactionDTOBuilder setAmount(BigDecimal amount) {
			this.amount = amount;
			return this;
		}

		public TransactionDTOBuilder setDetails(String details) {
			this.details = details;
			return this;
		}

		public TransactionDTOBuilder setTransactionDate(Date transactionDate) {
			this.transactionDate = transactionDate;
			return this;
		}

		public TransactionDTOBuilder setTransactionReference(Long transactionReference) {
			this.transactionReference = transactionReference;
			return this;
		}

		public UserTransactionDTO build() {
			return new UserTransactionDTO(this);
		}

	}

}
