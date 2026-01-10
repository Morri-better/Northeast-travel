package com.example.travelservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PARAM_ERROR(402,"库存不足"),
    
    SUCCESS(200, "操作成功"),
    
    PARAM_INVALID(400, "参数校验失败"),
    
    NOT_FOUND(404, "资源不存在"),
    
    BIZ_ERROR(500, "业务处理失败"),
    
    SYSTEM_ERROR(500, "系统内部错误"),
    
    UNAUTHORIZED(401, "未授权"),
    
    FORBIDDEN(403, "禁止访问");
    
    private final int code;
    private final String message;
}

