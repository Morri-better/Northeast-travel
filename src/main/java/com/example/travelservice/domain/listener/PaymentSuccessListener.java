package com.example.travelservice.domain.listener;

import com.example.travelservice.domain.event.PaymentSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class PaymentSuccessListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPaymentSuccess(PaymentSuccessEvent event) {
        log.info("事务提交后执行, orderId={}, payNo={}",
                event.getOrderId(), event.getPayNo());
    }
}