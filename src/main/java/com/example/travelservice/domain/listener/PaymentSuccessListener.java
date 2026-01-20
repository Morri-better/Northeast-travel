package com.example.travelservice.domain.listener;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.travelservice.common.enums.ProductType;
import com.example.travelservice.domain.event.PaymentSuccessEvent;
import com.example.travelservice.entity.Order;
import com.example.travelservice.mapper.OrderMapper;
import com.example.travelservice.mapper.ProductsMapper;
import com.example.travelservice.mapper.ToursMapper;
import com.example.travelservice.service.StockDeductLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentSuccessListener {

    private final OrderMapper orderMapper;
    private final ToursMapper toursMapper;
    private final ProductsMapper productsMapper;
    private final StockDeductLogService stockDeductLogService;
    // TODO 考虑是否下线这个监听器，已经没有它需要解决的问题

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPaymentSuccess(PaymentSuccessEvent event) {
        Long orderId = event.getOrderId();
        String payNo = event.getPayNo();

        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            log.error("【扣库存】订单不存在，orderId={}", orderId);
            return;
        }

        Integer quantity = order.getQuantity();
        ProductType productType = order.getProductType();
        Long itemId = order.getProductId();
        String itemType = productType.name();

        try {
            int updated;
            switch (productType) {
                case TOUR:
                    updated = deductTourStock(itemId, quantity);
                    break;
                case PRODUCT:
                    updated = deductProductStock(itemId, quantity);
                    break;
                default:
                    throw new IllegalArgumentException("unknown itemType: " + itemType);
            }

            if (updated == 0) {
                stockDeductLogService.recordFail(orderId, payNo, itemType, itemId, quantity, "deductStock updated=0 (maybe not enough stock)");
                return;
            }

            stockDeductLogService.markSuccess(orderId, itemType, itemId);

        } catch (Exception e) {
            stockDeductLogService.recordFail(orderId, payNo, itemType, itemId, quantity, e.getMessage());
        }
    }

    private int deductTourStock(Long tourId, Integer quantity) {
        int updated = toursMapper.update(
                null,
                Wrappers.<com.example.travelservice.entity.Tours>lambdaUpdate()
                        .eq(com.example.travelservice.entity.Tours::getId, tourId)
                        .ge(com.example.travelservice.entity.Tours::getStock, quantity)
                        .setSql("stock = stock - " + quantity)
        );

        if (updated == 0) {
            throw new IllegalStateException(
                    "旅游团库存不足或不存在，tourId=" + tourId
            );
        }
        return updated;
    }

    private int deductProductStock(Long productId, Integer quantity) {
        int updated = productsMapper.update(
                null,
                Wrappers.<com.example.travelservice.entity.Products>lambdaUpdate()
                        .eq(com.example.travelservice.entity.Products::getId, productId)
                        .ge(com.example.travelservice.entity.Products::getStock, quantity)
                        .setSql("stock = stock - " + quantity)
        );

        if (updated == 0) {
            throw new IllegalStateException(
                    "商品库存不足或不存在，productId=" + productId
            );
        }
        return updated;
    }
}