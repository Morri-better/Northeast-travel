package com.example.travelservice.mapper;

import com.example.travelservice.dto.request.ProductPageQueryRequest;
import com.example.travelservice.entity.TravelGuideCategory;
import com.example.travelservice.entity.travelGuide;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TravelGuideMapper {
    @Select("select * from snow_tourism_item")
    public Page<travelGuide> select(ProductPageQueryRequest request) ;
@Select("select * from snow_scenic where snow_scenic.catagory_id = #{catagoryId}")
    List<TravelGuideCategory> getSubjectById(Long catagoryId);
}