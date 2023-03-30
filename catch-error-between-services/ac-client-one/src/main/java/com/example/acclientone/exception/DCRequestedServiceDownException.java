package com.example.acclientone.exception;

public class DCRequestedServiceDownException extends RuntimeException {
    private int errorCode;

    public DCRequestedServiceDownException() {
    }

    public DCRequestedServiceDownException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DCRequestedServiceDownException(Throwable cause, String message) {
        super(message, cause);
    }

    public DCRequestedServiceDownException(String message, Throwable cause) {
        super(message, cause);
    }

    public DCRequestedServiceDownException(Throwable cause) {
        super(cause);
    }

    protected DCRequestedServiceDownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}