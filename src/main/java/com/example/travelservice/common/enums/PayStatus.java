package com.example.travelservice.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum PayStatus {
    INIT("INIT", "初始化"),
    SUCCESS("SUCCESS", "支付成功");

    @EnumValue
    private final String code;
    private final String desc;

    PayStatus(String code, String desc) {
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

