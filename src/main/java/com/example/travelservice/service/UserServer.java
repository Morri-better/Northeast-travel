package com.example.travelservice.service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.example.travelservice.common.exception.AccountLockedException;
import com.example.travelservice.common.exception.AccountNotFoundException;
import com.example.travelservice.common.exception.CodeErrorException;
import com.example.travelservice.common.exception.PasswordErrorException;
import com.example.travelservice.dto.request.UserLoginRequest;
import com.example.travelservice.dto.request.RegisterRequest;
import com.example.travelservice.dto.response.UserLoginResponse;
import com.example.travelservice.entity.User;
import com.example.travelservice.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;


@Service
public class UserServer {
    @Autowired
    private UserMapper userMapper;

    public User select(Long id) {
        return userMapper.select(id);
    }

    public User login(UserLoginRequest request, HttpSession session) {
        String phone = request.getPhone();
        String password = request.getPassword();
        String captcha = request.getCaptcha();

//        //判断验证码
//        String storedCaptcha = (String) session.getAttribute("captcha");
//        if (storedCaptcha == null || !storedCaptcha.equalsIgnoreCase(captcha)) {
//           throw new CodeErrorException("验证码错误");
//        }
//
//        //删除验证码
//        session.removeAttribute("captcha");

        //1、根据手机号查询数据库中的数据
        User user = userMapper.getByUsername(phone);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException("账号不存在");
        }

        //密码比对
        //对前端传过来的明文密码进行MD5加密
        // TODO 后期升级为BCrypt
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println("input md5 len=" + password.length());
        System.out.println("db pwd len=" + user.getPassword().length());
        System.out.println("db pwd=[" + user.getPassword() + "]");

        String dbPwd = user.getPassword();
        if (!password.equals(dbPwd == null ? null : dbPwd.trim())) {
            throw new PasswordErrorException("密码错误");
        }

        if (user.getStatus() ==0) {
            //账号被锁定
            throw new AccountLockedException("账号被锁定");
        }

        //3、返回实体对象
        return user;
    }

    public User register(RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String phone = request.getPhone();

        User existByPhone = userMapper.getByUsername(phone);
        if (existByPhone != null) {
            throw new CodeErrorException("手机号已存在");
        }

        User existByUsername = userMapper.getByUsername(username);
        if (existByUsername != null) {
            throw new CodeErrorException("用户名已存在");
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setName(username);
        user.setStatus(1);

        userMapper.insert(user);

        return user;
    }
}
