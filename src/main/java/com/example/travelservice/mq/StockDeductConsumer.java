package com.example.travelservice.mq;

import com.alibaba.fastjson.JSON;
import com.example.travelservice.dto.PaymentSuccessMsg;
import com.example.travelservice.service.BizConsumeLogService;
import com.example.travelservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = "stock_deduct",
        consumerGroup = "stock-deduct-consumer-group",
        consumeMode = ConsumeMode.CONCURRENTLY
)
public class StockDeductConsumer implements RocketMQListener<MessageExt> {

    private static final String EVENT_TYPE = "PAYMENT_SUCCESS";

    private final BizConsumeLogService bizConsumeLogService;
    private final StockService stockService;

    @Override
    public void onMessage(MessageExt msg) {
        String body = new String(msg.getBody(), StandardCharsets.UTF_8);
        PaymentSuccessMsg event = JSON.parseObject(body, PaymentSuccessMsg.class);

        String payNo = event.getPayNo();
        Long orderId = event.getOrderId();

        boolean first = bizConsumeLogService.tryAcquire(EVENT_TYPE, orderId, payNo);
        if (!first) {
            log.info("重复业务事件已忽略，payNo={}, orderId={}", payNo, orderId);
            return;
        }

        try {
            stockService.deductStockAfterPayment(orderId);
            log.info("库存扣减成功，payNo={}, orderId={}", payNo, orderId);

        } catch (Exception ex) {
            log.error("库存扣减失败，payNo={}, orderId={}", payNo, orderId, ex);

            return;
        }
    }
}