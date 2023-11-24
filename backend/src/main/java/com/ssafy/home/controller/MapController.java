package com.ssafy.home.controller;

import com.ssafy.home.dto.DongCodeDto;
import com.ssafy.home.dto.HouseInfoDto;
import com.ssafy.home.dto.requestDto.LocationRequest;
import com.ssafy.home.dto.responseDto.RecentDealResponse;
import com.ssafy.home.service.MapService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/map")
@Slf4j
public class MapController {

    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }


    @GetMapping("/search/region")
    @ApiOperation(value = "지역명으로 검색하여 지역 목록 추출", notes = "%LIKE%")
    public ResponseEntity<?> searchListByRegion(@RequestParam(value = "keyword") String keyword) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<DongCodeDto> dongCodeList = mapService.searchListByRegion(keyword);
            resultMap.put("msg", "search by region Successfully");
            resultMap.put("object", dongCodeList);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @GetMapping("/search/apt")
    @ApiOperation(value = "아파트명으로 검색하여 아파트 목록 추출", notes = "%LIKE%")
    public ResponseEntity<?> searchListByApt(@RequestParam(value = "keyword") String keyword) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<HouseInfoDto> houseInfoList = mapService.searchListByApt(keyword);
            resultMap.put("msg", "search by APT Successfully");
            resultMap.put("object", houseInfoList);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @GetMapping("/search/{dongcode}")
    @ApiOperation(value = "지역구/동에 해당하는 아파트들의 최신 거래가 가져오기")
    public ResponseEntity<?> getRecentHouseDealByDongCode(@PathVariable("dongcode") String dongCode) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<RecentDealResponse> recentDealResponse = mapService.getRecentHouseDealByDongCode(dongCode);
            resultMap.put("msg", "get deal information Successfully");
            resultMap.put("object", recentDealResponse);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @PostMapping("/search/location")
    @ApiOperation(value = "위도, 경도에 따라 그 범위 안에 있는 아파트 최근 실거래가 정보 가져오기(최신 거래 순)")
    public ResponseEntity<?> getRecentHouseDealByLocation(@RequestBody LocationRequest locationRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<RecentDealResponse> recentDealResponse = mapService.getRecentHouseDealByLocation(locationRequest);
            resultMap.put("msg", "get recent deal information Successfully");
            resultMap.put("object", recentDealResponse);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @PostMapping("/search/distance")
    @ApiOperation(value = "위도, 경도에 따라 그 범위 안에 있는 아파트 최근 실거래가 정보 가져오기(가장 가까운 거리순)")
    public ResponseEntity<?> getRecentHouseDealByDistance(@RequestBody LocationRequest locationRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<RecentDealResponse> recentDealResponse = mapService.getRecentHouseDealByDistance(locationRequest);
            resultMap.put("msg", "get deal information by distance Successfully");
            resultMap.put("object", recentDealResponse);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }
}
