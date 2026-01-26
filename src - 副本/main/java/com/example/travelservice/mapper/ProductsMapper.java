package com.example.travelservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travelservice.entity.Products;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductsMapper extends BaseMapper<Products> {
}