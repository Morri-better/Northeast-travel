package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.entity.TourSteam;
import com.example.travelservice.entity.User;
import com.example.travelservice.service.TourService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tours")
@RequiredArgsConstructor
@Slf4j
public class TourController extends BaseController{
    private final TourService tourService;

    @GetMapping
    public ApiResponse<PageResult> select(ProductPageQueryRequest  request){
        log.info("分页条件：{}", request);
        PageResult pageResult = tourService.select(request);

        return success(pageResult);
    }
    @GetMapping("/{id}")
    public ApiResponse<TourSteam> getUserById(@PathVariable Long id) {
        log.info("查询id：{}", id);
        TourSteam tourSteam = tourService.getUserById(id);
        return success(tourSteam);
    }




}
