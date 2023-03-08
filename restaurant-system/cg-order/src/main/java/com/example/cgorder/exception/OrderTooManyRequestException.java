package com.example.cgorder.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderTooManyRequestException extends RuntimeException{
    public OrderTooManyRequestException(String message){
        super(message);
    }
}
