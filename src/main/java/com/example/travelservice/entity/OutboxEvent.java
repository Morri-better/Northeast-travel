package com.example.travelservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("outbox_event")
public class OutboxEvent {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String eventType;

    private String bizKey;

    private String payload;

    private String status;

    private Integer retryCount;

    private LocalDateTime nextRetryTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}