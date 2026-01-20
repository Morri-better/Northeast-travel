package com.example.travelservice.dto;

import lombok.Data;

/**
 * 支付成功消息类
 */
@Data
public class PaymentSuccessMsg {
    private Long orderId;
    private String payNo;
}