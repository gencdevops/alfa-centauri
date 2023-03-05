package com.example.cgorder.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InConsistentProductPriceException extends RuntimeException{
    public InConsistentProductPriceException(String message){
        super(message);
    }
}
