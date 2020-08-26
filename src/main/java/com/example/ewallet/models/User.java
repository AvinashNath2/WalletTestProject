package com.example.ewallet.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String userName;
	@Column(unique=true)
	private String emailId;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dateCreated;
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<UserTransaction> userTransactions = new HashSet<>();

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

	public Set<UserTransaction> getTransactions() {
		return userTransactions;
	}

	public void setTransactions(Set<UserTransaction> userTransactions) {
		this.userTransactions = userTransactions;
	}

	/** Builder : for user */
	public static class UserAccountBuilder {

		private Long id;
		private String userName;
		private String email;
		private Date dateCreated;

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