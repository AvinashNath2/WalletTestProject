package com.example.ewallet.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String userName;
	@Column(unique=true, nullable = false)
	private String emailId;
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dateCreated;
	private BigDecimal balance = BigDecimal.ZERO;

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public User(String userName, String emailId, Date dateCreated) {
		this.userName = userName;
		this.emailId = emailId;
		this.dateCreated = dateCreated;
	}

	public User(Long id, String userName, String emailId, Date dateCreated) {
		this.id = id;
		this.userName = userName;
		this.emailId = emailId;
		this.dateCreated = dateCreated;
	}

	public User(UserAccountBuilder builder) {
		id = builder.id;
		userName = builder.userName;
		emailId = builder.email;
		dateCreated = builder.dateCreated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String email) {
		this.emailId = email;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/** Builder : for user */
	public static class UserAccountBuilder {

		private Long id;
		private String userName;
		private String email;
		private Date dateCreated;
		private BigDecimal balance;

		public UserAccountBuilder setBalance(BigDecimal balance) {
			this.balance = balance;
			return this;
		}

		public Long getId() {
			return id;
		}

		public UserAccountBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public UserAccountBuilder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public UserAccountBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserAccountBuilder setDateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
			return this;
		}

		public User build() {
			return new User(this);
		}

	}

}