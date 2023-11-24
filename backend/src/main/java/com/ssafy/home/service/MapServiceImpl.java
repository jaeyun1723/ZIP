package com.ssafy.home.service;

import com.ssafy.home.dto.DongCodeDto;
import com.ssafy.home.dto.HouseInfoDto;
import com.ssafy.home.dto.LatLng;
import com.ssafy.home.dto.requestDto.LocationRequest;
import com.ssafy.home.dto.responseDto.RecentDealResponse;
import com.ssafy.home.repository.MapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    @Autowired
    public MapServiceImpl(MapMapper mapMapper) {
        this.mapMapper = mapMapper;
    }

    @Override
    public List<DongCodeDto> searchListByRegion(String keyword) throws Exception {
        return mapMapper.searchListByRegion(keyword);
    }

    @Override
    public List<HouseInfoDto> searchListByApt(String keyword) throws Exception {
        return mapMapper.searchListByApt(keyword);
    }

    @Override
    public List<RecentDealResponse> getRecentHouseDealByDongCode(String dongCode) throws Exception {
        String code = new String();
        if (dongCode.substring(5, 8).equals("000"))
            code = dongCode.substring(0, 5);
        else
            code = dongCode.substring(0, 8);
        return mapMapper.getRecentHouseDealByDongCode(code);

    }

    @Override
    public List<RecentDealResponse> getRecentHouseDealByLocation(LocationRequest locationRequest) throws Exception {
        List<RecentDealResponse> recentDealResponseList = mapMapper.getRecentHouseDealByLocation(locationRequest);
        if (recentDealResponseList.size() >= 80)
            recentDealResponseList.subList(0, 79);
        return recentDealResponseList;
    }

    @Override
    public List<RecentDealResponse> getRecentHouseDealByDistance(LocationRequest locationRequest) throws Exception {
        List<RecentDealResponse> recentDealResponseList = mapMapper.getRecentHouseDealByLocation(locationRequest);
        double lat = Double.parseDouble(locationRequest.getStart().getLat()) + Double.parseDouble(locationRequest.getEnd().getLat());
        double lng = Double.parseDouble(locationRequest.getStart().getLng()) + Double.parseDouble(locationRequest.getEnd().getLng());
        LatLng center = new LatLng(lat / 2.0, lng / 2.0);
        for (RecentDealResponse response : recentDealResponseList)
            response.setDistance(center, new LatLng(response.getLat(), response.getLng()));
        recentDealResponseList.sort((o1, o2) -> (int) (o1.getDistance() - o2.getDistance()));
        if (recentDealResponseList.size() >= 80)
            recentDealResponseList.subList(0, 79);
        return recentDealResponseList;
    }

}
