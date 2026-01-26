package com.example.travelservice.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Data
public class TourSteamRequest {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;        // 出发日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;           // 返回日期
    private String departureCity;           // 始发城市/集合地点
    private String destination;             // 目的地
    private Integer travelPeople;           // 旅行人数

    private int page;

    private int pageSize;
}
