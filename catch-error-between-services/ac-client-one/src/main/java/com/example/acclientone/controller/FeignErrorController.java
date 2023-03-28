package com.example.acclientone.controller;

import com.example.acclientone.feign.ServiceTwoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feign")
@RequiredArgsConstructor
public class FeignErrorController {
    private final ServiceTwoClient serviceTwoClient;

    @GetMapping("/get400")
    public ResponseEntity getError400(){
        return ResponseEntity.ok(serviceTwoClient.getError400());
    }

    @GetMapping("/get403")
    public ResponseEntity getError403(){
        return ResponseEntity.ok(serviceTwoClient.getError403());
    }

    @GetMapping("/get404")
    public ResponseEntity getError404(){
        return ResponseEntity.ok(serviceTwoClient.getError404());
    }

    @GetMapping("/get409")
    public ResponseEntity getError409(){
        return ResponseEntity.ok(serviceTwoClient.getError409());
    }

    @GetMapping("/get412")
    public ResponseEntity getError412(){
        return ResponseEntity.ok(serviceTwoClient.getError412());
    }

    @GetMapping("/get429")
    public ResponseEntity getError429(){
        return ResponseEntity.ok(serviceTwoClient.getError429());
    }

    @GetMapping("/get433")
    public ResponseEntity getError433(){
        return ResponseEntity.ok(serviceTwoClient.getError433());
    }

    @GetMapping("/get500")
    public ResponseEntity getError500(){
        return ResponseEntity.ok(serviceTwoClient.getError500());
    }

    @GetMapping("/get502")
    public ResponseEntity getError502(){
        return ResponseEntity.ok(serviceTwoClient.getError502());
    }
}