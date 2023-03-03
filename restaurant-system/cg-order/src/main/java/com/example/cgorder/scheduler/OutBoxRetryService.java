package com.example.cgorder.scheduler;



import com.example.cgorder.model.Order;
import com.example.cgorder.repository.OrderOutboxRepository;
import com.example.cgorder.service.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutBoxRetryService {

    private final OrderOutboxRepository orderOutboxRepository;
    private final ProducerService producerService;

    private final ObjectMapper objectMapper;

    public void retry() {
        orderOutboxRepository.findAllByOrderByIdAsc(Pageable.ofSize(10)).forEach(orderOutbox -> {
            producerService.sendMessage(orderOutbox.getOrderPayload());
            orderOutboxRepository.deleteById(orderOutbox.getOrderId());
        });



}
