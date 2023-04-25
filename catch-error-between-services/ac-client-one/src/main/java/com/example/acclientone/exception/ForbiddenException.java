package com.example.acclientone.exception;

public class ForbiddenException extends RuntimeException {
    private int errorCode;
    private String errorStatus;

    public ForbiddenException() {
    }

    public ForbiddenException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ForbiddenException(String message, String errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    protected ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
