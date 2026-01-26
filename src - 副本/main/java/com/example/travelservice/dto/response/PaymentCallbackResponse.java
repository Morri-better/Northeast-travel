package com.example.travelservice.dto.response;

import com.example.travelservice.common.enums.OrderStatus;
import com.example.travelservice.common.enums.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付回调响应类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCallbackResponse {
    
    private Long orderId;
    
    private OrderStatus orderStatus;
    
    private PayStatus payStatus;
}

