package com.example.ewallet.exceptions;

/** Given User not available */
public class UserNotFoundException extends Exception {

	private String message;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
