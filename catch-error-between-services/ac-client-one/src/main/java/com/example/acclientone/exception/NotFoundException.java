package com.example.acclientone.exception;

public class NotFoundException extends RuntimeException{
    private int errorCode;
    private String errorStatus;

    public NotFoundException() {
    }

    public NotFoundException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NotFoundException(String message, String errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    protected NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
