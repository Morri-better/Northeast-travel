package com.example.travelservice.service;

import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.Content;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.entity.User;
import com.example.travelservice.mapper.ContentMapper;
import com.example.travelservice.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentServer {
    @Autowired
    private ContentMapper contentMapper;


    public PageResult select(ProductPageQueryRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        Page<Content> page=contentMapper.select(request);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public Content getContentById(Long id) {
        return contentMapper.getContentById(id);
    }
}
