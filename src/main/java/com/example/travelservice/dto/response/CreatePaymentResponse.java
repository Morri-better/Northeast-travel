package com.example.travelservice.dto.response;

import com.example.travelservice.common.enums.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建支付响应类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponse {
    
    private Long paymentId;
    
    private String payNo;
    
    private PayStatus payStatus;
}

