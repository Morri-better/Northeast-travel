package com.example.travelservice.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.travelservice.entity.OutboxEvent;
import com.example.travelservice.mapper.OutboxEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Outbox事件服务类
 */
@Service
@RequiredArgsConstructor
public class OutboxEventService {

    private final OutboxEventMapper outboxEventMapper;

    public void savePaymentSuccessEvent(
            String payNo,
            Long orderId,
            Long productId,
            Integer quantity
    ) {
        OutboxEvent event = new OutboxEvent();
        event.setEventType("PAYMENT_SUCCESS");
        event.setBizKey(payNo);
        event.setPayload(buildPayload(orderId, productId, quantity));
        event.setStatus("NEW");
        event.setRetryCount(0);
        event.setNextRetryTime(LocalDateTime.now());
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());

        outboxEventMapper.insert(event);
    }

    public List<OutboxEvent> findNewEvents(int limit) {
        return outboxEventMapper.selectList(
                Wrappers.<OutboxEvent>lambdaQuery()
                        .eq(OutboxEvent::getStatus, "NEW")
                        .le(OutboxEvent::getNextRetryTime, LocalDateTime.now())
                        .orderByAsc(OutboxEvent::getId)
                        .last("limit " + limit)
        );
    }

    public void markSent(Long eventId) {
        outboxEventMapper.update(
                null,
                Wrappers.<OutboxEvent>lambdaUpdate()
                        .eq(OutboxEvent::getId, eventId)
                        .set(OutboxEvent::getStatus, "SENT")
                        .set(OutboxEvent::getUpdatedAt, LocalDateTime.now())
        );
    }

    public void markRetry(Long eventId, int retryCount, LocalDateTime nextRetryTime) {
        outboxEventMapper.update(
                null,
                Wrappers.<OutboxEvent>lambdaUpdate()
                        .eq(OutboxEvent::getId, eventId)
                        .set(OutboxEvent::getRetryCount, retryCount)
                        .set(OutboxEvent::getNextRetryTime, nextRetryTime)
                        .set(OutboxEvent::getUpdatedAt, LocalDateTime.now())
        );
    }

    private String buildPayload(Long orderId, Long productId, Integer quantity) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("orderId", orderId);
        payload.put("productId", productId);
        payload.put("quantity", quantity);
        return com.alibaba.fastjson.JSON.toJSONString(payload);
    }
}