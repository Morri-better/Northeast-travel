package com.example.travelservice.dto.request;

import com.example.travelservice.common.enums.ProductType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 创建订单请求类
 */
@Data
public class CreateOrderRequest {
    
    @NotNull(message = "产品类型不能为空")
    private ProductType productType;
    
    @NotNull(message = "产品ID不能为空")
    @Positive(message = "产品ID必须大于0")
    private Long productId;
    
    @NotNull(message = "数量不能为空")
    @Positive(message = "数量必须大于0")
    private Integer quantity;
}

