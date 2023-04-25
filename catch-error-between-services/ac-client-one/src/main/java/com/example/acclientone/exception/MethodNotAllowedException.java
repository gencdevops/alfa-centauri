package com.example.acclientone.exception;

public class MethodNotAllowedException extends RuntimeException {
    private int errorCode;
    private String errorStatus;

    public MethodNotAllowedException() {
    }

    public MethodNotAllowedException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MethodNotAllowedException(String message, String errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public MethodNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodNotAllowedException(Throwable cause) {
        super(cause);
    }

    protected MethodNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
