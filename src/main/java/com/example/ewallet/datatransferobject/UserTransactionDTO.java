package com.example.ewallet.datatransferobject;

import java.math.BigDecimal;
import java.util.Date;

import com.example.ewallet.models.TransactionStatus;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/** Client Facing Model of Transaction **/
public class UserTransactionDTO {

	@ApiModelProperty(required = false, hidden = true)
	private Long id;

	private BigDecimal amount;
	private String details;
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date transactionUpdatedDate;
	private String transactionHash;
	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;

	public UserTransactionDTO() {
	}

	public UserTransactionDTO(TransactionDTOBuilder builder) {
		id = builder.id;
		amount = builder.amount;
		details = builder.details;
		transactionUpdatedDate = builder.transactionUpdatedDate;
		transactionHash = builder.transactionHash;
		transactionStatus = builder.transactionStatus;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
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

	public Date getTransactionUpdatedDate() {
		return transactionUpdatedDate;
	}

	public void setTransactionUpdatedDate(Date transactionUpdatedDate) {
		this.transactionUpdatedDate = transactionUpdatedDate;
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public static class TransactionDTOBuilder {

		private Long id;
		private BigDecimal amount;
		private String details;
		private Date transactionUpdatedDate;
		private String transactionHash;
		private TransactionStatus transactionStatus;

		public TransactionDTOBuilder setTransactionStatus(TransactionStatus transactionStatus) {
			this.transactionStatus = transactionStatus;
			return this;
		}

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

		public TransactionDTOBuilder setTransactionUpdatedDate(Date transactionUpdatedDate) {
			this.transactionUpdatedDate = transactionUpdatedDate;
			return this;
		}

		public TransactionDTOBuilder setTransactionHash(String transactionHash) {
			this.transactionHash = transactionHash;
			return this;
		}

		public UserTransactionDTO build() {
			return new UserTransactionDTO(this);
		}

	}

}
