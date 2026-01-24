package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.entity.User;
import com.example.travelservice.service.UserServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController extends BaseController{
    private final UserServer userServer;

    @GetMapping("/profile/{id}")
    public ApiResponse<User> select(@PathVariable Long id){
        log.info("查询用户信息：{}", id);
        User user =userServer.select(id);
        return success(user);
    }

}
