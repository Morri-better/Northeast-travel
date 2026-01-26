package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.dto.request.CreateOrderRequest;
import com.example.travelservice.dto.response.CreateOrderResponse;
import com.example.travelservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController extends BaseController {
    
    private final OrderService orderService;
    
    @PostMapping
    public ApiResponse<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        // TODO 用户id业务还没做
        CreateOrderResponse response = orderService.createOrder(request, 1L);
        return success(response);
    }
}

