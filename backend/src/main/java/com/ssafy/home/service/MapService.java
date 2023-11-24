package com.ssafy.home.service;

import com.ssafy.home.dto.DongCodeDto;
import com.ssafy.home.dto.HouseInfoDto;
import com.ssafy.home.dto.requestDto.LocationRequest;
import com.ssafy.home.dto.responseDto.RecentDealResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MapService {


    List<DongCodeDto> searchListByRegion(String keyword) throws Exception;

    List<HouseInfoDto> searchListByApt(String keyword) throws Exception;

    List<RecentDealResponse> getRecentHouseDealByDongCode(String dongCode) throws Exception;

    List<RecentDealResponse> getRecentHouseDealByLocation(LocationRequest locationRequest) throws Exception;

    List<RecentDealResponse> getRecentHouseDealByDistance(LocationRequest locationRequest) throws Exception;
}

