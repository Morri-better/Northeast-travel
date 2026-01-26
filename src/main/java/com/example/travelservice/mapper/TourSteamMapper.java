package com.example.travelservice.mapper;

import com.example.travelservice.dto.request.TourSteamRequest;
import com.example.travelservice.dto.response.TourSteamResponse;
import com.example.travelservice.entity.Tours;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TourSteamMapper {
    Page<TourSteamResponse> select(TourSteamRequest request);
}
