package com.example.travelservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 聊天请求类
 */
@Data
public class ChatRequest {

    @NotBlank(message = "消息不能为空")
    private String message;
}