package com.example.cgorder.service;


import com.example.cgorder.model.Order;
import com.example.cgorder.model.OrderStatus;
import com.example.cgorder.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.support.serializer.DeserializationException;


import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderEventListenerService {
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @RetryableTopic(
            attempts = "5",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            exclude = {SerializationException.class, DeserializationException.class, JsonProcessingException.class}
    )
    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.group.id}")
    public void handleMessage(String order, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws JsonProcessingException {
        Order orderString = objectMapper.readValue(order, Order.class);
        log.info(")");
        Optional<Order> orderOptional = orderRepository.findById(orderString.getOrderId());
        orderOptional.ifPresent(this::updateOrderFromOrderOutboxRetryScheduler);
    }

    @DltHandler
    public void handleDlt(String order, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Message: {} handled by dlq topic: {}", order, topic);

        //TODO : burada slacke gidecegiz
    }


    private void updateOrderFromOrderOutboxRetryScheduler(Order order) {
        order.setOrderStatus(OrderStatus.PREPARING);
        orderRepository.save(order);
    }
}
