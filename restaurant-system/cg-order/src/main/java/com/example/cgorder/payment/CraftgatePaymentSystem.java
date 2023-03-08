package com.example.cgorder.payment;

import com.example.cgcommon.request.OrderItemRequestDTO;
import com.example.cgorder.model.Card;
import io.craftgate.Craftgate;
import io.craftgate.model.Currency;
import io.craftgate.model.PaymentGroup;
import io.craftgate.model.PaymentPhase;
import io.craftgate.request.CreatePaymentRequest;
import io.craftgate.request.dto.PaymentItem;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CraftgatePaymentSystem implements PaymentSystem {

    private Currency CURRENCY = Currency.TRY;
    private Craftgate craftgate = new Craftgate("sandbox-MkOuIWzmHFxsIKCaDzSVsARStdmJqlrc", "sandbox-JsgPbznSePymfcSiDHelyQzMknDqAYNJ", "https://sandbox-api.craftgate.io");

    @Override
    public Object pay(List<OrderItemRequestDTO> orderItemRequestDTOList, Card card) {
        CreatePaymentRequest paymentRequest = createPaymentRequest(orderItemRequestDTOList, card);

        return craftgate.payment().createPayment(paymentRequest);
    }

    /**
     * Create craftgate payment request object
     */
    private CreatePaymentRequest createPaymentRequest(List<OrderItemRequestDTO> orderItemRequestDTOList, Card card){
        BigDecimal totalPrice = BigDecimal.ZERO;

        List<PaymentItem> paymentItems = new ArrayList<>();

        for (OrderItemRequestDTO orderItemRequestDTO : orderItemRequestDTOList) {
            totalPrice = totalPrice.add(orderItemRequestDTO.getTotalPrice());

            paymentItems.add(
                    PaymentItem.builder()
                            .price(orderItemRequestDTO.getUnitPrice())
                            .externalId(UUID.randomUUID().toString())
                            .build()
            );
        }

        return CreatePaymentRequest.builder()
                .price(totalPrice)
                .paidPrice(totalPrice)
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(CURRENCY)
                .conversationId("456d1297-908e-4bd6-a13b-4be31a6e47d5")
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(io.craftgate.request.dto.Card.builder()
                        .cardHolderName(card.getHolderName())
                        .cardNumber(card.getCardNumber())
                        .expireYear(String.valueOf(card.getExpireYear()))
                        .expireMonth(String.valueOf(card.getExpireMonth()))
                        .cvc(card.getCvc())
                        .build())
                .items(paymentItems)
                .build();
    }
}