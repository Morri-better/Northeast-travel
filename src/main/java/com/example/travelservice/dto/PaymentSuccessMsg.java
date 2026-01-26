package com.example.travelservice.dto;

import lombok.Data;

@Data
public class PaymentSuccessMsg {
    private Long orderId;
    private String payNo;
}