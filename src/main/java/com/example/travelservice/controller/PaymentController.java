package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.dto.request.CreatePaymentRequest;
import com.example.travelservice.dto.request.PaymentCallbackRequest;
import com.example.travelservice.dto.response.CreatePaymentResponse;
import com.example.travelservice.dto.response.PaymentCallbackResponse;
import com.example.travelservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments/mock")
@RequiredArgsConstructor
public class PaymentController extends BaseController {
    
    private final PaymentService paymentService;
    
    @PostMapping("/pay")
    public ApiResponse<CreatePaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        CreatePaymentResponse response = paymentService.createPayment(request);
        return success(response);
    }
    
    @PostMapping("/callback")
    public ApiResponse<PaymentCallbackResponse> callback(@Valid @RequestBody PaymentCallbackRequest request) {
        PaymentCallbackResponse response = paymentService.handleCallback(request);
        return success(response);
    }
}

