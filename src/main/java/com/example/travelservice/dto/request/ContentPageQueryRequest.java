package com.example.travelservice.dto.request;

import lombok.Data;

@Data
public class ContentPageQueryRequest {
    private int page;

    private int pageSize;
}
