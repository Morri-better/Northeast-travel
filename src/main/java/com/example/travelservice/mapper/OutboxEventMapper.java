package com.example.travelservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.travelservice.entity.OutboxEvent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutboxEventMapper extends BaseMapper<OutboxEvent> {
}