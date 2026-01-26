package com.example.travelservice.service;

import com.example.travelservice.dto.response.AiChatResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class TourismAiClient {

    private final WebClient webClient;

    public TourismAiClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:5001")
                .build();
    }

    public String chat(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        AiChatResponse resp = webClient.post()
                .uri("/chat")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(AiChatResponse.class)
                .timeout(Duration.ofSeconds(30))
                .block();

        if (resp == null) {
            throw new RuntimeException("AI 服务无响应");
        }

        if (!resp.isSuccess()) {
            throw new RuntimeException(resp.getError());
        }

        return resp.getReply();
    }
}