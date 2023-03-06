package com.example.cgrestaurant.feign;


import com.example.cgcommon.dto.response.OrderResponseDTO;


import com.example.cgcommon.request.PlaceOrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "cg-order" , url = "http://localhost:8474")
public interface OrderFeignClient{

    @PostMapping("/api/v1/orders/place-order")
    OrderResponseDTO placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequestDTO);


    @GetMapping("/v1/orders/dummy")
    ResponseEntity<String> dummy();
}
