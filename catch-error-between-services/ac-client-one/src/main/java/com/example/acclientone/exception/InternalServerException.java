package com.example.acclientone.exception;

public class InternalServerException extends RuntimeException {
    private int errorCode;
    private String errorStatus;

    public InternalServerException() {
    }

    public InternalServerException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public InternalServerException(String message, String errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }

    protected InternalServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
