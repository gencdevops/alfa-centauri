package com.example.cgrestaurant.exception;

public class ProductPriceNotFoundException extends RuntimeException{
    public ProductPriceNotFoundException(String message) {
        super(message);
    }
}
