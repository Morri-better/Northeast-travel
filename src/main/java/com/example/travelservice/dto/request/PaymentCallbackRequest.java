package com.example.travelservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCallbackRequest {
    
    @NotNull(message = "支付单号不能为空")
    private String payNo;
    
    @NotNull(message = "支付结果不能为空")
    private Boolean success;
}

