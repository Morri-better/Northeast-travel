package com.example.travelservice.domain.event;

import lombok.Getter;

@Getter
public class PaymentSuccessEvent {

    private final Long orderId;
    private final String payNo;

    public PaymentSuccessEvent(Long orderId, String payNo) {
        this.orderId = orderId;
        this.payNo = payNo;
    }
}