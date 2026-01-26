package com.example.travelservice.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.travelservice.common.enums.OrderStatus;
import com.example.travelservice.common.enums.ProductType;
import com.example.travelservice.entity.Order;
import com.example.travelservice.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    
    private final OrderMapper orderMapper;
    
    public int insert(Order order) {
        return orderMapper.insert(order);
    }
    
    public int updateById(Order order) {
        return orderMapper.updateById(order);
    }
    
    public Order selectById(Long id) {
        return orderMapper.selectById(id);
    }
    
    public Order selectOneByOrderNo(String orderNo) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOrderNo, orderNo);
        wrapper.last("LIMIT 1");
        return orderMapper.selectOne(wrapper);
    }
    
    public Order selectOneByUserId(Long userId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        wrapper.last("LIMIT 1");
        return orderMapper.selectOne(wrapper);
    }
    
    public Order selectOneByProductId(Long productId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getProductId, productId);
        wrapper.last("LIMIT 1");
        return orderMapper.selectOne(wrapper);
    }
    
    public Order selectOneByStatus(OrderStatus status) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getStatus, status);
        wrapper.last("LIMIT 1");
        return orderMapper.selectOne(wrapper);
    }
    
    public Order selectOneByProductType(ProductType productType) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getProductType, productType);
        wrapper.last("LIMIT 1");
        return orderMapper.selectOne(wrapper);
    }
}

