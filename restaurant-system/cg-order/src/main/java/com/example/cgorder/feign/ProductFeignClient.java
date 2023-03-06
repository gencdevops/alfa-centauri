package com.example.cgorder.feign;


import com.example.cgcommon.dto.response.ProductPriceResponseDto;
import com.example.cgcommon.request.ProductPricesRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;


@FeignClient(name = "cg-restaurant")
public interface ProductFeignClient {

    @PostMapping("/api/v1/products/branches/{branchId}/product-prices")
    List<ProductPriceResponseDto> getProductPrices(@PathVariable UUID branchId,
                                                   @RequestBody ProductPricesRequestDto productPricesRequestDto);

    @PostMapping("/api/v1/products/product-status")
    List<ProductPriceResponseDto> getProductStatus(@RequestBody ProductPricesRequestDto productPricesRequestDto);

}
