package com.example.ewallet.exceptions;

import lombok.Getter;

/** Common Exception */
@Getter
public class SystemException extends RuntimeException {
    private String internalMessage;
    private int httpStatusCode;

    private SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(int httpStatusCode, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String toString() {
        return super.toString() + " internalMessage= " + internalMessage;
    }
}