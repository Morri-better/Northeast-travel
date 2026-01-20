package com.example.travelservice.dto.response;

import com.example.travelservice.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 创建订单响应类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {
    
    private Long orderId;
    
    private String orderNo;
    
    private OrderStatus status;
    
    private BigDecimal amount;
}

