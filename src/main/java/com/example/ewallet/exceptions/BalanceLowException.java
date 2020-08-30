package com.example.ewallet.exceptions;

/** Exception when balance insufficient */
public class BalanceLowException extends Exception {

	private String message;

	public BalanceLowException() {
		super();
	}

	public BalanceLowException(String message) {
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
