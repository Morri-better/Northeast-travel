package com.example.travelservice.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.travelservice.common.enums.ProductType;
import com.example.travelservice.entity.StockDeductLog;
import com.example.travelservice.mapper.ProductsMapper;
import com.example.travelservice.mapper.StockDeductLogMapper;
import com.example.travelservice.mapper.ToursMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockCompensationJob {
    private final StockDeductLogMapper stockDeductLogMapper;
    private final ToursMapper toursMapper;
    private final ProductsMapper productsMapper;
    @Scheduled(fixedDelay = 60000)
    public void compensate() {
        List<StockDeductLog> logs = stockDeductLogMapper.pickDue(100);
        if (logs.isEmpty()) {
            return;
        }
        log.info("【库存补偿】开始处理 {} 条待补偿记录", logs.size());

        for (StockDeductLog logee : logs) {
            int locked=stockDeductLogMapper.markRetrying(logee.getId());
            if (locked == 0) {
                continue;
            }
            try {

                int updated=0;
                switch (logee.getItemType()) {
                    case "TOUR":
                        updated = deductTourStock(logee.getItemId(), logee.getQuantity());
                        break;
                    case "PRODUCT":
                        updated = deductProductStock(logee.getItemId(), logee.getQuantity());
                        break;
                    default:
                        throw new IllegalArgumentException("unknown itemType: " + logee.getItemType());
                }

                if (updated == 0) {
                    stockDeductLogMapper.upsertFail(
                            logee.getOrderId(),
                            logee.getPayNo(),
                            logee.getItemType(),
                            logee.getItemId(),
                            logee.getQuantity(),
                            "deductStock updated=0 (maybe not enough stock)",
                            8
                    );
                    continue;
                }
                stockDeductLogMapper.markSuccess(logee.getOrderId(), logee.getItemType(), logee.getItemId());
            } catch (Exception e) {
                log.error("【库存补偿失败】id={}, orderId={}, itemType={}, itemId={}",
                        logee.getId(), logee.getOrderId(), logee.getItemType(), logee.getItemId(), e);
            }
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