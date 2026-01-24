package com.example.travelservice.mq;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class RocketMqProducer {

    private final RocketMQTemplate rocketMQTemplate;

    public void send(String topic, String payload, String bizKey) {
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader("KEYS", bizKey)
                .build();

        SendResult r =rocketMQTemplate.syncSend(topic, message);
        log.info("排查topic有没有发出去:sendStatus={}, msgId={}", r.getSendStatus(), r.getMsgId());
    }
}