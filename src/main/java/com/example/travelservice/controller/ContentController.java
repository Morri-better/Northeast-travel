package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.Content;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.service.ContentServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@Slf4j
@RequiredArgsConstructor
public class ContentController extends BaseController{
    @Autowired
    private final ContentServer contentServer;
    @GetMapping
    public ApiResponse<PageResult> getContent(@RequestBody ProductPageQueryRequest  request){
        log.info("分页条件: {}",request);
        PageResult pageResult =contentServer.select(request);
        return success(pageResult);
    }
    @GetMapping("/{id}")
    public ApiResponse<Content> getContentById(@PathVariable Long id) {
        log.info("查询id: {}", id);
        Content content = contentServer.getContentById(id);
        return success(content);
    }
}
