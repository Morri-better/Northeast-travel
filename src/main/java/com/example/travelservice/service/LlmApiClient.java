package com.example.travelservice.service;

import com.example.travelservice.dto.ChatMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LlmApiClient {

    private static final String API_KEY = "ark-b5167081-0aa4-4eb3-90ef-8fbf78a3e807-1963a";
    private static final String ENDPOINT_ID = "ep-20260424182545-6wnkv";
    private static final String API_URL = "https://ark.cn-beijing.volces.com/api/v3/chat/completions";

    private final RestTemplate restTemplate;

    public LlmApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public String callModel(List<ChatMessage> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", ENDPOINT_ID);
        requestBody.put("messages", messages);
        requestBody.put("stream", false);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);
            Map<String, Object> body = response.getBody();

            if (body != null && body.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return (String) message.get("content");
                }
            }
            return "大模型返回格式异常，未找到 choices 字段";

        } catch (Exception e) {
            e.printStackTrace();
            return "调用豆包大模型失败，请检查网络或 API 额度。错误信息: " + e.getMessage();
        }
    }
}
