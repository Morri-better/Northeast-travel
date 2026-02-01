package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.PageResult;
import com.example.travelservice.entity.Products;
import com.example.travelservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController extends BaseController{
    private final ProductService productService;
    @GetMapping
    public ApiResponse<PageResult> getProducts(@RequestBody ProductPageQueryRequest  request){
        log.info("获取分页信息: {}", request);
        PageResult pageResult = productService.getProducts(request);
        return success(pageResult);
    }
    @GetMapping("/{id}")
    public ApiResponse<Products> getProductById(@PathVariable Long id){
        log.info("获取产品id: {}", id);
        Products  products = productService.getProductById(id);
        return success(  products);
    }

}
