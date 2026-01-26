package com.example.travelservice.mapper;

import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.Content;
import com.example.travelservice.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ContentMapper {
@Select("select * from content")
    Page<Content> select(ProductPageQueryRequest request);
@Select("select * from content where id = #{id}")
    Content getContentById(Long id);
}
