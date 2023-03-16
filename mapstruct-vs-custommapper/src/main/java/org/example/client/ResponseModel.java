package org.example.client;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ResponseModel {
    private String message;
    private List<UserDto> data;
}
