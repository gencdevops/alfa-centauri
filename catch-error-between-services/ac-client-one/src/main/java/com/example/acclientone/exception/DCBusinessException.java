package com.example.acclientone.exception;

public class DCBusinessException extends RuntimeException {
    private int errorCode;
    private String errorStatus;

    public DCBusinessException() {
    }

    public DCBusinessException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DCBusinessException(String message, String errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public DCBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DCBusinessException(Throwable cause) {
        super(cause);
    }

    protected DCBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}