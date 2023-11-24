package com.ssafy.home.repository;

import com.ssafy.home.dto.DongCodeDto;
import com.ssafy.home.dto.HouseDealDto;
import com.ssafy.home.dto.HouseInfoDto;
import com.ssafy.home.dto.RatingDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseMapper {
    List<HouseDealDto> getHouseDealInfo(long aptCode) throws Exception;

    HouseInfoDto getHouseInfo(long aptCode) throws Exception;

    DongCodeDto getDongCodeInfo(String dongCode) throws Exception;

    RatingDto getRating(long aptCode) throws Exception;
}
