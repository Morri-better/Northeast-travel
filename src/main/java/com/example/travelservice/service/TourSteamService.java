package com.example.travelservice.service;

import com.example.travelservice.dto.request.TourSteamRequest;
import com.example.travelservice.dto.response.TourSteamResponse;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.entity.Tours;
import com.example.travelservice.mapper.TourSteamMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourSteamService {
    @Autowired
    private TourSteamMapper tourSteamMapper;

    public PageResult select(TourSteamRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        Page<TourSteamResponse> page=tourSteamMapper.select(request);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
