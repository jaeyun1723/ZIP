package com.ssafy.home.repository;

import com.ssafy.home.dto.DongCodeDto;
import com.ssafy.home.dto.HouseInfoDto;
import com.ssafy.home.dto.requestDto.LocationRequest;
import com.ssafy.home.dto.responseDto.RecentDealResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapMapper {

    List<DongCodeDto> searchListByRegion(String keyword) throws Exception;

    List<HouseInfoDto> searchListByApt(String keyword) throws Exception;

    List<RecentDealResponse> getRecentHouseDealByDongCode(String code) throws Exception;

    List<RecentDealResponse> getRecentHouseDealByLocation(LocationRequest locationRequest) throws Exception;
}
