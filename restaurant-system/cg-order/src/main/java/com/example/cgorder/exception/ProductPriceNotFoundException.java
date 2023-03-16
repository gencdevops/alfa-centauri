package com.example.cgorder.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductPriceNotFoundException extends RuntimeException{
    public ProductPriceNotFoundException(String message){
        super(message);
    }
}
