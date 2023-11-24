package com.ssafy.home.dto.responseDto;

import com.ssafy.home.dto.DongCodeDto;
import com.ssafy.home.dto.HouseDealDto;
import com.ssafy.home.dto.HouseInfoDto;
import com.ssafy.home.dto.RatingDto;
import com.ssafy.home.vo.Rating;
import lombok.Data;

import java.util.List;

@Data
public class HouseResponse {
    private DongCodeDto dongCodeDto;
    private HouseInfoDto houseInfoDto;
    private List<HouseDealDto> houseDealDtoList;
    private RatingDto ratingDto;
}
