package com.example.travelservice.job;

import com.example.travelservice.entity.OutboxEvent;
import com.example.travelservice.mq.RocketMqProducer;
import com.example.travelservice.service.OutboxEventService;
import com.example.travelservice.util.RetryPolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxRelayJob {

    private static final int BATCH_SIZE = 50;
    private static final String STOCK_DEDUCT_TOPIC = "stock_deduct";

    private final OutboxEventService outboxEventService;
    private final RocketMqProducer rocketMqProducer;
    private final RetryPolicy retryPolicy;

    @Scheduled(fixedDelay = 60000)
    public void relay() {
        List<OutboxEvent> events = outboxEventService.findNewEvents(BATCH_SIZE);

        for (OutboxEvent event : events) {
            try {
                rocketMqProducer.send(
                        STOCK_DEDUCT_TOPIC,
                        event.getPayload(),
                        event.getBizKey()
                );

                outboxEventService.markSent(event.getId());

            } catch (Exception ex) {
                log.error("Outbox 发送失败, id={}", event.getId(), ex);

                int nextRetry = event.getRetryCount() + 1;
                if (retryPolicy.canRetry(nextRetry)) {
                    outboxEventService.markRetry(
                            event.getId(),
                            nextRetry,
                            retryPolicy.nextRetryTime(nextRetry)
                    );
                } else {
                    log.error("Outbox 超过最大重试次数, id={}", event.getId());
                }
            }
        }
    }
}