package com.example.travelservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.travelservice.common.enums.OrderStatus;
import com.example.travelservice.common.enums.ProductType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Order {
    
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    private String orderNo;
    
    private Long userId;
    
    @TableField("product_type")
    private ProductType productType;
    
    private Long productId;
    
    private Integer quantity;
    
    private BigDecimal amount;
    
    @TableField("status")
    private OrderStatus status;
    
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(value = "paid_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime paidAt;
    
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}

