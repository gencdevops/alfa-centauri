package com.example.customstatuscode.model;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    public static long serialVersionUID = 101010;

    private Long userId;
}
