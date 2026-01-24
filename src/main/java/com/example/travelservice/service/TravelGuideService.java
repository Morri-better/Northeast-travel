package com.example.travelservice.service;

import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.entity.TravelGuideCategory;
import com.example.travelservice.mapper.TravelGuideMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.travelservice.entity.travelGuide;

import java.util.List;

@Service
public class TravelGuideService {
    @Autowired
    private TravelGuideMapper travelGuideMapper;

    public PageResult getSubjects(ProductPageQueryRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        Page<travelGuide> page=travelGuideMapper.select(request);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public List<TravelGuideCategory> getSubjectById(Long catagoryId) {
        return travelGuideMapper.getSubjectById(catagoryId);
    }
}
