package com.example.acclientone.exception;

public class PreconditionException extends RuntimeException{
    private int errorCode;
    private String errorStatus;

    public PreconditionException() {
    }

    public PreconditionException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public PreconditionException(String message, String errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public PreconditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreconditionException(Throwable cause) {
        super(cause);
    }

    protected PreconditionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
