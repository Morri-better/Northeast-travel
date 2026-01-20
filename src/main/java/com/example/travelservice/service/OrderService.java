package com.example.travelservice.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.travelservice.common.BusinessException;
import com.example.travelservice.common.ErrorCode;
import com.example.travelservice.common.enums.OrderStatus;
import com.example.travelservice.dto.request.CreateOrderRequest;
import com.example.travelservice.dto.response.CreateOrderResponse;
import com.example.travelservice.entity.Order;
import com.example.travelservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单服务类，提供订单创建、查询和状态更新等功能
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final PriceService priceService;
    
    /**
     * 创建新订单
     * 根据请求参数计算订单金额，创建订单记录，并生成订单号
     * 
     * @param request 创建订单的请求参数，包含产品类型、产品ID和数量等信息
     * @param userId 用户ID
     * @return 创建订单的响应结果，包含订单ID、订单号、状态和金额
     * @throws BusinessException 当订单创建失败时抛出业务异常
     */
    @Transactional(rollbackFor = Exception.class)
    public CreateOrderResponse createOrder(CreateOrderRequest request, Long userId) {
        
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "产品数量要大于0");
        }
        
        BigDecimal unitPrice = priceService.getPriceAndValidate(
                request.getProductType(),
                request.getProductId()
        );
        
        BigDecimal amount = unitPrice.multiply(BigDecimal.valueOf(request.getQuantity()));
        
        Order order = new Order();
        order.setId(IdWorker.getId());
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setProductType(request.getProductType());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setAmount(amount);
        order.setStatus(OrderStatus.CREATED);
        
        int insertResult = orderRepository.insert(order);
        if (insertResult <= 0) {
            throw new BusinessException(ErrorCode.BIZ_ERROR, "创建订单失败");
        }
        
        return CreateOrderResponse.builder()
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .status(order.getStatus())
                .amount(amount)
                .build();
    }
    
    /**
     * 根据订单ID查询订单信息
     * 
     * @param orderId 订单ID
     * @return 订单信息
     * @throws BusinessException 当订单不存在时抛出业务异常
     */
    public Order getOrderById(Long orderId) {
        Order order = orderRepository.selectById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "订单不存在");
        }
        return order;
    }

    
    /**
     * 生成订单号
     * 订单号格式：前缀(O) + 时间戳 + 雪花ID后6位
     * 
     * @return 生成的订单号
     */
    private String generateOrderNo() {
        return "O" + System.currentTimeMillis() + IdWorker.getIdStr().substring(0, 6);
    }
}

