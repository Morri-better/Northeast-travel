package com.example.travelservice.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum ProductType {
    TOUR("TOUR", "旅游产品"),
    PRODUCT("PRODUCT", "普通商品");

    @EnumValue
    private final String code;
    private final String desc;

    ProductType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

