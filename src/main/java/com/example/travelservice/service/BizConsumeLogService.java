package com.example.travelservice.service;

import com.example.travelservice.entity.BizConsumeLog;
import com.example.travelservice.mapper.BizConsumeLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BizConsumeLogService {

    private final BizConsumeLogMapper mapper;

    public boolean tryAcquire(String eventType, Long orderId, String payNo) {
        BizConsumeLog log = new BizConsumeLog();
        log.setEventType(eventType);
        log.setOrderId(orderId);
        log.setBizKey(orderId + ":" + payNo);
        log.setStatus("SUCCESS");
        log.setCreatedAt(LocalDateTime.now());

        try {
            mapper.insert(log);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}