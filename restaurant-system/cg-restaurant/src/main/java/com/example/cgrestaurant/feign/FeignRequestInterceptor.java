package com.example.cgrestaurant.feign;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {



	@Override
	public void apply(RequestTemplate template) {

	}
}
