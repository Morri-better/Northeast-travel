package com.example.travelservice.entity;

import lombok.Data;

@Data
public class TravelGuideCategory {
    private String name;           // 活动名称
    private String location;       // 地理位置
    private String mainActivity;   // 主要冰雪活动
    private int temperature;       // 平均气温（℃）
    private Long catagoryId;
}
