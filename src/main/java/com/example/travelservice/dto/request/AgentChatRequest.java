package com.example.travelservice.dto.request;

import com.example.travelservice.dto.ChatMessage;
import lombok.Data;

import java.util.List;

@Data
public class AgentChatRequest {
    private List<ChatMessage> messages;
}
