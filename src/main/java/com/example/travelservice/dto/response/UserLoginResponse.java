package com.example.travelservice.dto.response;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class UserLoginResponse {
    private Long id;

    private String userName;
    private String phone;

    private String name;

    private String token;
}
