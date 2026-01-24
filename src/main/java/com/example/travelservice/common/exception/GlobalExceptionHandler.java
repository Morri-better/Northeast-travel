package com.example.travelservice.common.exception;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.common.BusinessException;
import com.example.travelservice.common.ErrorCode;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleBusinessException(BusinessException e) {
        String traceId = MDC.get("traceId");
        log.warn("业务异常 [traceId: {}]: {}", traceId, e.getMessage());
        return ApiResponse.fail(e.getErrorCode());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String traceId = MDC.get("traceId");
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数校验失败 [traceId: {}]: {}", traceId, message);
        return ApiResponse.fail(ErrorCode.PARAM_INVALID.getCode(), message);
    }
    
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleBindException(BindException e) {
        String traceId = MDC.get("traceId");
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数绑定失败 [traceId: {}]: {}", traceId, message);
        return ApiResponse.fail(ErrorCode.PARAM_INVALID.getCode(), message);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleConstraintViolationException(ConstraintViolationException e) {
        String traceId = MDC.get("traceId");
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.warn("约束校验失败 [traceId: {}]: {}", traceId, message);
        return ApiResponse.fail(ErrorCode.PARAM_INVALID.getCode(), message);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException e) {
        String traceId = MDC.get("traceId");
        log.warn("参数非法 [traceId: {}]: {}", traceId, e.getMessage());
        return ApiResponse.fail(ErrorCode.PARAM_INVALID.getCode(), e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleException(Exception e) {
        String traceId = MDC.get("traceId");
        log.error("系统异常 [traceId: {}]", traceId, e);
        return ApiResponse.fail(ErrorCode.SYSTEM_ERROR);
    }
}

