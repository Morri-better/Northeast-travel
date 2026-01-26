package com.example.travelservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TourSteamResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal adultPrice;
    private String images;
}
