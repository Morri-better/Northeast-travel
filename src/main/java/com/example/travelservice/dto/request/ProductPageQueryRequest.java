package com.example.travelservice.dto.request;

import lombok.Data;

@Data
public class ProductPageQueryRequest {
    private Integer page;

    private Integer pageSize;
}
