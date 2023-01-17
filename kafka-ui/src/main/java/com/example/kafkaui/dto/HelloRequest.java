package com.example.kafkaui.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HelloRequest {

    private String message;
    private Long senderId;
    private int messageCount;

}