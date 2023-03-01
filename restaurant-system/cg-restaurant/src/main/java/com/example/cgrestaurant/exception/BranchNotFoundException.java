package com.example.cgrestaurant.exception;

public class BranchNotFoundException extends RuntimeException{
    public BranchNotFoundException(String message) {
        super(message);
    }
}
