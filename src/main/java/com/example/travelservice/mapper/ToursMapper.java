package com.example.travelservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travelservice.entity.Tours;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ToursMapper extends BaseMapper<Tours> {
}