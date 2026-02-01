package com.example.travelservice.mapper;

import com.example.travelservice.dto.request.UserLoginRequest;
import com.example.travelservice.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User select(Long id);
@Select("select * from user where phone = #{phone}")
    User getByUsername(String phone);

    @Select("select * from user where phone = #{phone}")
    User getByPhone(String phone);

    @Insert("INSERT INTO user (username, name, password, phone, sex, id_number, status, avatar, create_time) " +
            "VALUES (#{username}, #{name}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{avatar}, #{createTime})")
    int insert(User user);
}
