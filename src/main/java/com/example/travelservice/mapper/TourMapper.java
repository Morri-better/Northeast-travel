package com.example.travelservice.mapper;

import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.TourSteam;
import com.example.travelservice.entity.Tours;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TourMapper {
    @Select("select * from tours")

    Page<Tours> select(ProductPageQueryRequest request);
    @Select("select * from tour_steam where id = #{id}")
    TourSteam getUserById(Long id);
}
