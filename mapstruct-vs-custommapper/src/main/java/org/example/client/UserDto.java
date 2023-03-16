package org.example.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private int salary;
    private String title;
    private String grade;
    private String role;
    private String department;
    private AddressDto addressDto;
}
