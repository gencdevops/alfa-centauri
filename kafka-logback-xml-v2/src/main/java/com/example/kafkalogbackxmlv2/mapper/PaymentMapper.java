package com.example.kafkalogbackxmlv2.mapper;

import com.example.kafkalogbackxmlv2.client.dto.request.PaymentRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.PaymentRequestWithoutSender;
import com.example.kafkalogbackxmlv2.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "PaymentMapperImpl", componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    Payment toPaymentFromPaymentRequest(PaymentRequest paymentRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senderId", ignore = true)
    Payment toPaymentFromPaymentRequestWithoutSender(PaymentRequestWithoutSender paymentRequest);

}
