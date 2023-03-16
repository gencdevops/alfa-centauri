package org.example.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {
    private String addressId;
    private String address;
    private String city;
    private String country;
    private String postCode;
}
