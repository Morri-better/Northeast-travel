package com.example.travelservice.dto.response;

import com.example.travelservice.common.enums.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponse {
    
    private Long paymentId;
    
    private String payNo;
    
    private PayStatus payStatus;
}

