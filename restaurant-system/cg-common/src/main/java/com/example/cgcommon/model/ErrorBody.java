package com.example.cgcommon.model;

import lombok.*;


@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorBody {
    private Integer errorCode;
    private String errorDescription;
}