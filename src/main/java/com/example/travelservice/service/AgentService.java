package com.example.travelservice.service;

import com.example.travelservice.dto.ChatMessage;
import com.example.travelservice.dto.request.AgentChatRequest;
import com.example.travelservice.dto.response.AgentChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgentService {

    private final LlmApiClient llmApiClient;
    private final ObjectMapper objectMapper;

    public AgentService(LlmApiClient llmApiClient, ObjectMapper objectMapper) {
        this.llmApiClient = llmApiClient;
        this.objectMapper = objectMapper;
    }

    public AgentChatResponse processChat(AgentChatRequest request) {

        List<ChatMessage> fullContext = new ArrayList<>();

        fullContext.add(new ChatMessage("system", buildSystemPrompt()));

        if (request.getMessages() != null) {
            fullContext.addAll(request.getMessages());
        }

        String rawLlmResponse = llmApiClient.callModel(fullContext);

        return formatOutput(rawLlmResponse);
    }

    private String buildSystemPrompt() {
        return "你是一个专业的东北旅游推荐Agent。你的任务是通过多轮对话了解用户的【出发地、天数、预算、偏好】。\n" +
                "【工具模拟指令】：\n" +
                "你不具备外部联网能力，但请根据你的知识库直接模拟以下工具的结果：\n" +
                "- 天气工具：根据用户出行的月份，直接给出现实合理的东北气温与穿衣建议。\n" +
                "- 地图工具：直接规划合理的城市流转顺序，避免绕路，并估算车程。\n" +
                "- 酒店工具：直接给出合理的住宿地段建议和参考均价。\n" +
                "【输出强制规范】：\n" +
                "如果信息还不全，请直接用自然语言继续追问用户。\n" +
                "如果信息已收集全，决定出具路线方案时，你的回复【必须】分为两部分：\n" +
                "第一部分：一段简短的自然语言总结。\n" +
                "第二部分：使用 JSON 格式包裹的详细路线，必须被 ```json 和 ``` 包裹。";
    }

    private AgentChatResponse formatOutput(String rawResponse) {
        AgentChatResponse response = new AgentChatResponse();

        if (rawResponse.contains("```json")) {
            try {
                int startIndex = rawResponse.indexOf("```json") + 7;
                int endIndex = rawResponse.indexOf("```", startIndex);
                String jsonStr = rawResponse.substring(startIndex, endIndex).trim();

                Object routeData = objectMapper.readValue(jsonStr, Object.class);

                String textOnly = rawResponse.substring(0, rawResponse.indexOf("```json")).trim();

                response.setReplyText(textOnly);
                response.setRouteData(routeData);
                response.setFinished(true);
            } catch (Exception e) {
                response.setReplyText(rawResponse);
                response.setFinished(false);
            }
        } else {
            response.setReplyText(rawResponse);
            response.setFinished(false);
        }

        return response;
    }
}
