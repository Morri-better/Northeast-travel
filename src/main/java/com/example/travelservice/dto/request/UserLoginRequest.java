package com.example.travelservice.dto.request;

import lombok.Data;

@Data
public class UserLoginRequest {
        private String phone;
        private String password;
        private String captcha;

}
