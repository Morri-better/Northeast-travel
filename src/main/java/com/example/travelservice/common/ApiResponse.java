package com.example.travelservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int code;
    
    private String message;
    
    private T data;
    
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }
    
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), message, data);
    }
    
    public static <T> ApiResponse<T> fail(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }
    
    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
    
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(ErrorCode.BIZ_ERROR.getCode(), message, null);
    }
}

