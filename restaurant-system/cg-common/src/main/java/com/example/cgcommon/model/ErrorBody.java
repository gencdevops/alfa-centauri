package com.example.cgcommon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class ErrorBody {
    private Integer errorCode;
    private String errorDescription;
}