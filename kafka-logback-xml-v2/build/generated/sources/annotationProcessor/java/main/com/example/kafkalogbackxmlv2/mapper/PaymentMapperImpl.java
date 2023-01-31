package com.example.kafkalogbackxmlv2.mapper;

import com.example.kafkalogbackxmlv2.client.dto.request.PaymentRequest;
import com.example.kafkalogbackxmlv2.client.dto.request.PaymentRequestWithoutSender;
import com.example.kafkalogbackxmlv2.model.entity.Payment;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-31T09:28:18+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toPaymentFromPaymentRequest(PaymentRequest paymentRequest) {
        if ( paymentRequest == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.senderId( paymentRequest.senderId() );
        payment.receiverId( paymentRequest.receiverId() );
        payment.amount( BigDecimal.valueOf( paymentRequest.amount() ) );
        payment.currency( paymentRequest.currency() );

        return payment.build();
    }

    @Override
    public Payment toPaymentFromPaymentRequestWithoutSender(PaymentRequestWithoutSender paymentRequest) {
        if ( paymentRequest == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.receiverId( paymentRequest.receiverId() );
        payment.amount( BigDecimal.valueOf( paymentRequest.amount() ) );
        payment.currency( paymentRequest.currency() );

        return payment.build();
    }
}
