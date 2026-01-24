package com.example.travelservice.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum OrderStatus {
    CREATED("CREATED", "已创建"),
    PAID("PAID", "已支付"),
    CANCELED("CANCELED", "已取消");

    @EnumValue
    private final String code;
    private final String desc;

    OrderStatus(String code, String desc) {
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

