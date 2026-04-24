package com.example.travelservice.controller;

import com.example.travelservice.dto.request.AgentChatRequest;
import com.example.travelservice.dto.response.AgentChatResponse;
import com.example.travelservice.service.AgentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/chat")
    public AgentChatResponse chat(@RequestBody AgentChatRequest request) {
        if (request.getMessages() == null || request.getMessages().isEmpty()) {
            throw new IllegalArgumentException("对话历史不能为空");
        }

        return agentService.processChat(request);
    }
}
