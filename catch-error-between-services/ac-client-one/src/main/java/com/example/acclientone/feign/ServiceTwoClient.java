package com.example.acclientone.feign;

import com.example.acclientone.config.ServiceTwoClientConfig;
import com.example.acclientone.model.ErrorBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ac-service-one", path = "/api/feign", configuration = ServiceTwoClientConfig.class)
public interface ServiceTwoClient {

    @GetMapping("/getError400")
    ErrorBody getError400();

    @GetMapping("/getError403")
    ErrorBody getError403();

    @GetMapping("/getError404")
    ErrorBody getError404();

    @GetMapping("/getError409")
    ErrorBody getError409();

    @GetMapping("/getError412")
    ErrorBody getError412();

    @GetMapping("/getError429")
    ErrorBody getError429();

    @GetMapping("/getError433")
    ErrorBody getError433();

    @GetMapping("/getError500")
    ErrorBody getError500();

    @GetMapping("/getError502")
    ErrorBody getError502();
}
