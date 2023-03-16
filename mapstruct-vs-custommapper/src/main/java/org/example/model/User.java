package org.example.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class User {
    private String employeeId;
    private String name;
    private String surname;
    private String email;
    private String tcNo;
    private int salary;
    private String title;
    private String grade;
    private String role;
    private String department;
    private LocalDate startDate;
    private LocalDate birthDate;
    private Address address;
}
