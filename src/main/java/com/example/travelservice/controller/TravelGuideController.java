package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.entity.TravelGuideCategory;
import com.example.travelservice.service.TravelGuideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@Slf4j
public class TravelGuideController extends BaseController{
    private final TravelGuideService travelGuideService;
    @GetMapping
    public ApiResponse<PageResult> getSubjects(@RequestBody ProductPageQueryRequest  request){
        log.info("获取分页信息: {}", request);
         PageResult pageResult = travelGuideService.getSubjects(request);
        return success(pageResult);
    }
    @GetMapping("/{catagoryId}")
public ApiResponse<List> getSubjectById(@PathVariable Long catagoryId){
        log.info("获取分页信息: {}", catagoryId);
        List<TravelGuideCategory> travelGuide = travelGuideService.getSubjectById(catagoryId);
        return success(travelGuide);
    }

}
