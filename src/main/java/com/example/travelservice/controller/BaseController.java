package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;

public class BaseController {
    
    protected <T> ApiResponse<T> success() {
        return ApiResponse.success();
    }
    
    protected static <T> ApiResponse<T> success(T data) {
        return ApiResponse.success(data);
    }
    
    protected <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.success(message, data);
    }
    
    protected <T> ApiResponse<T> fail(String message) {
        return ApiResponse.fail(message);
    }
}

