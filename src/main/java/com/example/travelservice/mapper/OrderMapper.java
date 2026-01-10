package com.example.travelservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travelservice.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}

