package com.example.cgorder.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductStatusExceptionException extends RuntimeException{
    public ProductStatusExceptionException(String message){
        super(message);
    }
}
