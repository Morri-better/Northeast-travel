package com.example.travelservice.service;

import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.entity.Products;
import com.example.travelservice.mapper.ProductMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;
    public PageResult getProducts(ProductPageQueryRequest request) {
        PageHelper.startPage(request.getPage(),request.getPageSize());
        Page<Products> page= productMapper.select(request);
        return new PageResult(page.getTotal(),page.getResult());

    }

    public Products getProductById(Long id) {
        return productMapper.selectById(id);
    }
}
