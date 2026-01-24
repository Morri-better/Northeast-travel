package com.example.travelservice.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.travelservice.common.BusinessException;
import com.example.travelservice.common.ErrorCode;
import com.example.travelservice.common.enums.OrderStatus;
import com.example.travelservice.common.enums.PayStatus;
import com.example.travelservice.common.enums.PayType;
import com.example.travelservice.dto.request.CreatePaymentRequest;
import com.example.travelservice.dto.request.PaymentCallbackRequest;
import com.example.travelservice.dto.response.CreatePaymentResponse;
import com.example.travelservice.dto.response.PaymentCallbackResponse;
import com.example.travelservice.entity.Order;
import com.example.travelservice.entity.Payment;
import com.example.travelservice.mapper.OrderMapper;
import com.example.travelservice.mapper.PaymentMapper;
import com.example.travelservice.repository.PaymentRepository;
import com.example.travelservice.service.OutboxEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;
    private final OutboxEventService outboxEventService;
    
    /**
     * 创建支付记录
     * 为指定订单创建支付记录，并生成支付号
     * 
     * @param request 创建支付的请求参数，包含订单ID
     * @return 创建支付的响应结果，包含支付ID、支付号和支付状态
     * @throws BusinessException 当订单状态不允许创建支付或支付记录创建失败时抛出业务异常
     */
    @Transactional(rollbackFor = Exception.class)
    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {
        Order order = orderService.getOrderById(request.getOrderId());
        
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessException(ErrorCode.BIZ_ERROR, "订单状态不允许创建支付");
        }
        
        Payment payment = new Payment();
        payment.setId(IdWorker.getId());
        payment.setOrderId(request.getOrderId());
        payment.setPayNo(generatePayNo());
        payment.setPayType(PayType.MOCK);
        payment.setPayStatus(PayStatus.INIT);
        
        int insertResult = paymentRepository.insert(payment);
        if (insertResult <= 0) {
            throw new BusinessException(ErrorCode.BIZ_ERROR, "创建支付记录失败");
        }
        
        return CreatePaymentResponse.builder()
                .paymentId(payment.getId())
                .payNo(payment.getPayNo())
                .payStatus(payment.getPayStatus())
                .build();
    }
    
    /**
     * 支付回调（模拟）
     * 幂等 & 并发安全设计：
     * 1) payNo 查 payment
     * 2) 如果 payment 已 SUCCESS：直接返回（幂等）
     * 3) success=true：尝试把 payment 从 INIT 改 SUCCESS（条件更新，避免并发重复）
     * 4) payment 改成功后：尝试把 order 从 CREATED 改 PAID（条件更新，避免重复/非法状态）
     * 5) 整个过程事务包裹：payment/order 一致性
     * 
     * @param request 支付回调请求参数，包含支付号和支付结果
     * @return 支付回调响应结果，包含订单ID、订单状态和支付状态
     * @throws BusinessException 当支付失败或订单状态异常时抛出业务异常
     */
    @Transactional(rollbackFor = Exception.class)
    public PaymentCallbackResponse handleCallback(PaymentCallbackRequest request) {
        
        if (request.getPayNo() == null || request.getPayNo().trim().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAM_INVALID, "payNo不能为空");
        }
        
        Payment payment = paymentMapper.selectOne(
                Wrappers.<Payment>lambdaQuery()
                        .eq(Payment::getPayNo, request.getPayNo())
                        .last("limit 1")
        );
        if (payment == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "支付单不存在: " + request.getPayNo());
        }
        
        if (payment.getPayStatus() == PayStatus.SUCCESS) {
            Order order = orderMapper.selectById(payment.getOrderId());
            if (order == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND, "订单不存在: " + payment.getOrderId());
            }
            return PaymentCallbackResponse.builder()
                    .orderId(order.getId())
                    .orderStatus(order.getStatus())
                    .payStatus(payment.getPayStatus())
                    .build();
        }
        // TODO 两次支付判断不知道是否多余，需要判断一下
        if (request.getSuccess() == null || !request.getSuccess()) {
            Order order = orderMapper.selectById(payment.getOrderId());
            if (order == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND, "订单不存在: " + payment.getOrderId());
            }
            return PaymentCallbackResponse.builder()
                    .orderId(order.getId())
                    .orderStatus(order.getStatus())
                    .payStatus(payment.getPayStatus())
                    .build();
        }
        
        LocalDateTime now = LocalDateTime.now();
        int payUpdated = paymentMapper.update(
                null,
                Wrappers.<Payment>lambdaUpdate()
                        .eq(Payment::getId, payment.getId())
                        .eq(Payment::getPayStatus, PayStatus.INIT)
                        .set(Payment::getPayStatus, PayStatus.SUCCESS)
                        .set(Payment::getUpdatedAt, now)
        );
        
        if (payUpdated == 0) {
            Payment latest = paymentMapper.selectById(payment.getId());
            Order order = orderMapper.selectById(payment.getOrderId());
            if (order == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND, "订单不存在: " + payment.getOrderId());
            }
            return PaymentCallbackResponse.builder()
                    .orderId(order.getId())
                    .orderStatus(order.getStatus())
                    .payStatus(latest.getPayStatus())
                    .build();
        }
        
        int orderUpdated = orderMapper.update(
                null,
                Wrappers.<Order>lambdaUpdate()
                        .eq(Order::getId, payment.getOrderId())
                        .eq(Order::getStatus, OrderStatus.CREATED)
                        .set(Order::getStatus, OrderStatus.PAID)
                        .set(Order::getPaidAt, now)
                        .set(Order::getUpdatedAt, now)
        );
        
        if (orderUpdated == 0) {
            throw new BusinessException(ErrorCode.BIZ_ERROR,
                    "订单状态异常，无法完成支付：orderId=" + payment.getOrderId());
        }

        Order order = orderMapper.selectById(payment.getOrderId());
        outboxEventService.savePaymentSuccessEvent(
                payment.getPayNo(),
                order.getId(),
                order.getProductId(),
                order.getQuantity()
        );

        return PaymentCallbackResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getStatus())
                .payStatus(PayStatus.SUCCESS)
                .build();
    }
    
    /**
     * 生成支付号
     * 支付号格式：前缀(P) + 时间戳 + 雪花ID后6位
     * 
     * @return 生成的支付号
     */
    private String generatePayNo() {
        return "P" + System.currentTimeMillis() + IdWorker.getIdStr().substring(0, 6);
    }
}

