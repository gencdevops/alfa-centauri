package com.example.propertyencryptionwithjasypt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorBody {
    private int errorCode;
    private String errorMessage;
}