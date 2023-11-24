package com.ssafy.home.service;

import com.ssafy.home.dto.DongCodeDto;
import com.ssafy.home.dto.HouseDealDto;
import com.ssafy.home.dto.HouseInfoDto;
import com.ssafy.home.dto.RatingDto;
import com.ssafy.home.repository.HouseMapper;
import com.ssafy.home.repository.MapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseMapper houseMapper;

    @Autowired
    public HouseServiceImpl(HouseMapper houseMapper) {
        this.houseMapper = houseMapper;
    }

    @Override
    public List<HouseDealDto> getHouseDealInfo(long aptCode) throws Exception {
        return houseMapper.getHouseDealInfo(aptCode);
    }

    @Override
    public HouseInfoDto getHouseInfo(long aptCode) throws Exception {
        return houseMapper.getHouseInfo(aptCode);
    }

    @Override
    public DongCodeDto getDongCodeInfo(String dongCode) throws Exception {
        return houseMapper.getDongCodeInfo(dongCode);
    }

    @Override
    public RatingDto getRating(long aptCode) throws Exception {
        return houseMapper.getRating(aptCode);
    }
}
