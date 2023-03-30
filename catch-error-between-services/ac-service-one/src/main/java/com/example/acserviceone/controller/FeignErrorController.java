package com.example.acserviceone.controller;

import com.example.acserviceone.model.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feign")
public class FeignErrorController {

    @GetMapping("/getError400")
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorBody getError400(){
        return ErrorBody.builder().errorDescription("400 error").errorCode(400).build();
    }

    @GetMapping("/getError403")
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity getError403(){
        return new ResponseEntity(ErrorBody.builder().errorDescription("403 error").errorCode(403).build(), HttpStatus.FORBIDDEN);
    }

    @GetMapping("/getError404")
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity getError404(){
        return new ResponseEntity(ErrorBody.builder().errorDescription("404 error").errorCode(404).build(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getError409")
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity getError409(){
        return new ResponseEntity(ErrorBody.builder().errorDescription("409 error").errorCode(409).build(), HttpStatus.CONFLICT);
    }

    @GetMapping("/getError412")
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity getError412(){
        return new ResponseEntity(ErrorBody.builder().errorDescription("412 error").errorCode(412).build(), HttpStatus.PRECONDITION_FAILED);
    }

    @GetMapping("/getError429")
    @ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity getError429(){
        return new ResponseEntity(ErrorBody.builder().errorDescription("429 error").errorCode(429).build(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/getError433")
    public ResponseEntity getError433(){
        return new ResponseEntity(ErrorBody.builder().errorDescription("433 error").errorCode(433).build(), null, 433);
    }

    @GetMapping("/getError500")
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity getError500(){
        return new ResponseEntity(ErrorBody.builder().errorDescription("500 error").errorCode(500).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getError502")
    @ResponseStatus(code = HttpStatus.BAD_GATEWAY)
    public ResponseEntity getError502(){
        return new ResponseEntity(ErrorBody.builder().errorDescription("502 error").errorCode(502).build(), HttpStatus.BAD_GATEWAY);
    }
}
