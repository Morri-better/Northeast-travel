package com.example.travelservice.dto.response;

import lombok.Data;

@Data
public class AgentChatResponse {
    private String replyText;
    private Object routeData;
    private boolean isFinished;
}
