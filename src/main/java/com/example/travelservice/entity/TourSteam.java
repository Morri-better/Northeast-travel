package com.example.travelservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourSteam {

        // 主键
        private Long id;

        // 基础信息
        private String title;                    // 旅游团名称
        private String productCode;              // 产品编号
        private String description;              // 产品描述
        private String feature;                  // 产品特色

        // 行程信息
        private String departureCity;           // 始发城市/集合地点
        private String destination;             // 目的地
        private String destinationDetail;       // 详细目的地

        // 时间信息
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate departureDate;        // 出发日期
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate returnDate;           // 返回日期
        private Integer durationDays;           // 行程天数
        private Integer durationNights;         // 行程夜数
        private LocalTime assemblyTime;         // 集合时间
        private String assemblyLocation;        // 集合地点

        // 价格信息
        private BigDecimal adultPrice;          // 成人价格
        private BigDecimal childPrice;          // 儿童价格
        private BigDecimal singleRoomSupplement; // 单房差

        // 容量信息
        private Integer minGroupSize;           // 成团最少人数
        private Integer maxGroupSize;           // 最多参团人数
        private Integer bookedCount;            // 已报名人数
        private Integer availableSeats;         // 剩余座位数

        // 状态信息
        private String status;                  // 团状态
        private LocalDateTime bookingDeadline;  // 报名截止时间

        // 服务标准
        private String accommodationStandard;   // 住宿标准
        private String mealStandard;            // 餐饮标准
        private String transportationStandard;  // 交通标准
        private Boolean hasGuide;               // 是否包含导游
        private Boolean hasInsurance;           // 是否包含保险

        // 扩展信息
        private String tags;                    // 标签，逗号分隔
        private String suitableCrowd;           // 适宜人群
        private Integer difficultyLevel;        // 难度等级 1-5
        private String coverImage;              // 封面图片URL
        private String images;                  // 产品图片URL列表，JSON格式

        // 供应商信息
        private Long supplierId;                // 供应商/旅行社ID
        private String supplierName;            // 供应商名称

        // 统计字段
        private Integer viewCount = 0;          // 浏览量
        private Integer collectionCount = 0;    // 收藏量
        private Integer reviewCount = 0;        // 评价数量
        private BigDecimal rating = BigDecimal.ZERO; // 评分

        // 系统字段
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        private Integer version = 0;

        // 枚举类
        public static class Status {
            public static final String DRAFT = "DRAFT";             // 草稿
            public static final String PUBLISHED = "PUBLISHED";     // 已发布
            public static final String OPEN_FOR_BOOKING = "OPEN";   // 报名中
            public static final String GROUPED = "GROUPED";         // 已成团
            public static final String FULL = "FULL";               // 已报满
            public static final String IN_PROGRESS = "IN_PROGRESS"; // 进行中
            public static final String COMPLETED = "COMPLETED";     // 已结束
            public static final String CANCELLED = "CANCELLED";     // 已取消
        }
 }
