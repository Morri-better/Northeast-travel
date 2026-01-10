package com.example.travelservice.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.travelservice.common.enums.PayStatus;
import com.example.travelservice.common.enums.PayType;
import com.example.travelservice.entity.Payment;
import com.example.travelservice.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {
    
    private final PaymentMapper paymentMapper;
    
    public int insert(Payment payment) {
        return paymentMapper.insert(payment);
    }
    
    public int updateById(Payment payment) {
        return paymentMapper.updateById(payment);
    }
    
    public Payment selectById(Long id) {
        return paymentMapper.selectById(id);
    }
    
    public Payment selectOneByOrderId(Long orderId) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getOrderId, orderId);
        wrapper.last("LIMIT 1");
        return paymentMapper.selectOne(wrapper);
    }
    
    public Payment selectOneByPayNo(String payNo) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getPayNo, payNo);
        wrapper.last("LIMIT 1");
        return paymentMapper.selectOne(wrapper);
    }
    
    public Payment selectOneByPayType(PayType payType) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getPayType, payType);
        wrapper.last("LIMIT 1");
        return paymentMapper.selectOne(wrapper);
    }
    
    public Payment selectOneByPayStatus(PayStatus payStatus) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getPayStatus, payStatus);
        wrapper.last("LIMIT 1");
        return paymentMapper.selectOne(wrapper);
    }
}

