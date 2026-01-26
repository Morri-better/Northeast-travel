package com.example.travelservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * AI聊天响应类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiChatResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private String reply;

    private String model;

    private String error;
}