package com.ssafy.home.service;

import com.ssafy.home.dto.DongCodeDto;
import com.ssafy.home.dto.HouseDealDto;
import com.ssafy.home.dto.HouseInfoDto;
import com.ssafy.home.dto.RatingDto;

import java.util.List;

public interface HouseService {


    List<HouseDealDto> getHouseDealInfo(long aptCode) throws Exception;

    HouseInfoDto getHouseInfo(long aptCode) throws Exception;

    DongCodeDto getDongCodeInfo(String dongCode) throws Exception;

    RatingDto getRating(long aptCode) throws Exception;
}

