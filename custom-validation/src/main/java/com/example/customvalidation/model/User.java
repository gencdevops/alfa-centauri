package com.example.customvalidation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String name;
    private String tckn;
    private String city;

    @Override
    public boolean equals(Object obj) {
        return obj == null || this.tckn.equals(((User) obj).getTckn());
    }
}
