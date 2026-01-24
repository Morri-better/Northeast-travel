package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.dto.request.ChatRequest;
import com.example.travelservice.dto.response.ChatResponse;
import com.example.travelservice.service.TourismAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class ChatController extends BaseController {

    private final TourismAiClient aiClient;

    @PostMapping("/chat")
    public ApiResponse<ChatResponse> chat(@Valid @RequestBody ChatRequest req) {
        try {
            String reply = aiClient.chat(req.getMessage());
            return success(new ChatResponse(reply));
        } catch (Exception e) {
            return fail("AI 服务暂时不可用");
        }
    }
}