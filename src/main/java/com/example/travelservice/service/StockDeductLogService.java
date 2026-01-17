package com.example.travelservice.service;

import com.example.travelservice.mapper.StockDeductLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockDeductLogService {

    private final StockDeductLogMapper mapper;
    private static final int MAX_RETRY = 8;

    public StockDeductLogService(StockDeductLogMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordFail(Long orderId, String payNo, String itemType, Long itemId, Integer qty, String reason) {
        mapper.upsertFail(orderId, payNo, itemType, itemId, qty, reason, MAX_RETRY);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markSuccess(Long orderId, String itemType, Long itemId) {
        mapper.markSuccess(orderId, itemType, itemId);
    }
}