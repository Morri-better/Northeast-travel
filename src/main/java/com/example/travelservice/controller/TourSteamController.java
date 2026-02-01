package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.dto.request.TourSteamRequest;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.service.TourSteamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/travel-plan")
@RequiredArgsConstructor
@Slf4j
public class TourSteamController extends BaseController{
    private final TourSteamService tourSteamServer;
    @GetMapping
    public ApiResponse<PageResult> select(@RequestBody TourSteamRequest  request){
        log.info("查询steam条件: {}", request);
        PageResult pageResult =tourSteamServer.select(request);
        return success(pageResult);
    }

}
