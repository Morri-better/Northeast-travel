package com.example.travelservice.controller;

import com.example.travelservice.common.ApiResponse;
import com.example.travelservice.dto.request.UserLoginRequest;
import com.example.travelservice.dto.response.UserLoginResponse;
import com.example.travelservice.entity.User;
import com.example.travelservice.service.UserServer;
import com.example.travelservice.util.JwtUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.example.travelservice.config.JwtProperties;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class UserLoginController extends BaseController{
    private final UserServer userService;

    private final JwtProperties jwtProperties;

    @GetMapping("/captcha")
    public Map<String, String> getCaptcha(HttpSession session) {
        // 生成验证码图片
        SpecCaptcha captcha = new SpecCaptcha(120, 40, 5);
        captcha.setCharType(Captcha.TYPE_DEFAULT); // 数字+大小写字母

        // 获取验证码文本
        String code = captcha.text();

        // 存到Session里（最简单的方式）
        session.setAttribute("captcha", code);

        // 返回给前端
        Map<String, String> result = new HashMap<>();
        result.put("image", captcha.toBase64());  // Base64图片
        result.put("code", code);  // 实际项目中这个别返回，这里只是演示
        return result;
    }


    //TODO 做图形验证还是短信验证
    @PostMapping ("/login/password")
    public ApiResponse<UserLoginResponse> login(@RequestBody UserLoginRequest request,HttpSession session){
        log.info("用户登录：{}", request);
        User user = userService.login(request,session);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginResponse respone = UserLoginResponse.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .userName(user.getUsername())
                .name(user.getName())
                .token(token)
                .build();

        return success(respone);

    }
}
