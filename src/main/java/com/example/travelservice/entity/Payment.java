package com.example.travelservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.travelservice.common.enums.PayStatus;
import com.example.travelservice.common.enums.PayType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("payment")
public class Payment {
    
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    private Long orderId;
    
    private String payNo;
    
    @TableField("pay_type")
    private PayType payType;
    
    @TableField("pay_status")
    private PayStatus payStatus;
    
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
    
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}

