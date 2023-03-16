package org.example.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Address {
    private String addressId;
    private String address;
    private String city;
    private String country;
    private String postCode;
    private User user;
}
