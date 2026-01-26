package com.example.travelservice.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum PayType {
    MOCK("MOCK", "模拟支付");

    @EnumValue
    private final String code;
    private final String desc;

    PayType(String code, String desc) {
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

