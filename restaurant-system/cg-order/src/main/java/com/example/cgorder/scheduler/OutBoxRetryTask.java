package com.example.cgorder.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutBoxRetryTask {

    private final OutBoxRetryService outBoxRetryService;

    public OutBoxRetryTask(OutBoxRetryService outBoxRetryService) {
        this.outBoxRetryService = outBoxRetryService;
    }


    // TODO : KARSI servis down olduysa kafkaya basamama durumu handle edilecek
    @Scheduled(fixedDelay = 5000)
    public void retry() {
        outBoxRetryService.retry();
    }
}
