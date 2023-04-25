package com.example.acclientone.exception;

public class ConflictException extends RuntimeException{
    private int errorCode;
    private String errorStatus;

    public ConflictException() {
    }

    public ConflictException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ConflictException(String message, String errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }

    protected ConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
