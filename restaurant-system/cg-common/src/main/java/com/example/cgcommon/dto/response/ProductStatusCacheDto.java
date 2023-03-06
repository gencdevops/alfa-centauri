package com.example.cgcommon.dto.response;


import com.example.cgcommon.model.ProductStatus;
import lombok.*;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStatusCacheDto implements Serializable {
    private String productId;
    private String productStatus;
}
