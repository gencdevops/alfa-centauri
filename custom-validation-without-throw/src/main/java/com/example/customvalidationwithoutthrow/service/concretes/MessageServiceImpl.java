package com.example.customvalidationwithoutthrow.service.concretes;

import com.example.customvalidationwithoutthrow.service.abstracts.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public boolean sendMessageToNumber(String sms, String telNo) {
        log.info("A message sent to number : {}", telNo);
        return true;
    }
}
