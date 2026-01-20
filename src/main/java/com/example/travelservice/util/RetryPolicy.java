package com.example.travelservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
public class RetryPolicy {

    private static final int MAX_RETRY = 5;

    public boolean canRetry(int retryCount) {
        return retryCount < MAX_RETRY;
    }

    public LocalDateTime nextRetryTime(int retryCount) {
        long delayMinutes = (long) Math.pow(2, retryCount);
        return LocalDateTime.now().plusMinutes(delayMinutes);
    }
}