package com.ssafy.home.controller;

import com.ssafy.home.dto.responseDto.HouseResponse;
import com.ssafy.home.service.HouseService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/house")
@Slf4j
public class HouseController {

    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("")
    @ApiOperation(value = "아파트 실거래가 조회")
    public ResponseEntity<?> getHouseDealInfo(@RequestParam(value = "aptCode") long aptCode) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            HouseResponse houseResponse = new HouseResponse();
            houseResponse.setHouseInfoDto(houseService.getHouseInfo(aptCode));
            houseResponse.setDongCodeDto(houseService.getDongCodeInfo(houseResponse.getHouseInfoDto().getDongCode()));
            houseResponse.setHouseDealDtoList(houseService.getHouseDealInfo(aptCode));
            houseResponse.setRatingDto(houseService.getRating(aptCode));
            resultMap.put("msg", "get house deal information Successfully");
            resultMap.put("object", houseResponse);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }
}
