package com.example.cgcommon.request;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStatusRequestDto implements Serializable {
    private List<UUID> productIds;
}
