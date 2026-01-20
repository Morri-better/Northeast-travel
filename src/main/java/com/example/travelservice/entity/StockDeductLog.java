package com.example.travelservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 库存扣减日志实体类
 */
@Data
@TableName("stock_deduct_log")
public class StockDeductLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;
    private String payNo;

    private String itemType;
    private Long itemId;
    private Integer quantity;

    private String status;
    private String failReason;

    private Integer retryCount;
    private LocalDateTime nextRetryAt;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}