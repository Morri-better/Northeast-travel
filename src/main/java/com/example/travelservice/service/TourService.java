package com.example.travelservice.service;

import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.entity.TourSteam;
import com.example.travelservice.entity.Tours;
import com.example.travelservice.mapper.TourMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {
    @Autowired
    private TourMapper tourMapper;

    public PageResult select(ProductPageQueryRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        Page<Tours> page=tourMapper.select(request);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public TourSteam getUserById(Long id) {
        return tourMapper.getUserById(id);
    }
}
