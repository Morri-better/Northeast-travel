package com.example.travelservice.service;

import com.example.travelservice.common.enums.ProductType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 价格服务类，提供产品价格计算功能
 */
@Service
public class PriceService {
    
    private static final BigDecimal TOUR_PRICE = new BigDecimal("1999.00");
    private static final BigDecimal PRODUCT_PRICE = new BigDecimal("99.00");
    
    /**
     * 根据产品类型和数量计算总价
     * 
     * @param productType 产品类型（旅游或商品）
     * @param quantity 产品数量
     * @return 计算后的总价格
     * @throws IllegalArgumentException 当产品类型不支持时抛出异常
     */
    public BigDecimal calculatePrice(ProductType productType, Integer quantity) {
        BigDecimal unitPrice = getUnitPrice(productType);
        return unitPrice.multiply(new BigDecimal(quantity));
    }
    
    /**
     * 根据产品类型获取单价并校验业务数据
     * 
     * @param productType 产品类型（旅游或商品）
     * @param productId 产品ID
     * @return 产品的单价
     * @throws IllegalArgumentException 当产品类型不支持时抛出异常
     */
    public BigDecimal getPriceAndValidate(ProductType productType, Long productId) {
        return getUnitPrice(productType);
    }
    
    /**
     * 根据产品类型获取单价
     * 
     * @param productType 产品类型
     * @return 产品单价
     * @throws IllegalArgumentException 当产品类型不支持时抛出异常
     */
    private BigDecimal getUnitPrice(ProductType productType) {
        // TODO priceservice有问题，价格暂时写死了，等到旅游团增加了以后再添加
        if (productType == ProductType.TOUR) {
            return TOUR_PRICE;
        } else if (productType == ProductType.PRODUCT) {
            return PRODUCT_PRICE;
        } else {
            throw new IllegalArgumentException("不支持的产品类型: " + productType);
        }
    }
}

