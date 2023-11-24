package com.ssafy.home.dto.responseDto;


import com.ssafy.home.dto.HouseDealDto;
import com.ssafy.home.dto.HouseInfoDto;
import com.ssafy.home.dto.LatLng;
import lombok.Data;

@Data
public class RecentDealResponse {
    private long no;
    private String dealAmount;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String area;
    private String floor;
    private String cancelDealType;
    private long aptCode;

    private int buildYear;
    private String roadName;
    private String roadNameBonbun;
    private String roadNameBubun;
    private String roadNameSeq;
    private String roadNameBasementCode;
    private String roadNameCode;
    private String dong;
    private String bonbun;
    private String bubun;
    private String sigunguCode;
    private String eubmyundongCode;
    private String dongCode;
    private String landCode;
    private String apartmentName;
    private String jibun;
    private String lng;
    private String lat;

    private double distance;

    public void setDistance(LatLng center, LatLng target) {
        this.distance = distance(center, target);
    }

    // 두 좌표 사이의 거리를 구하는 함수
    private static double distance(LatLng dot1, LatLng dot2) {
        double theta = dot1.getLng() - dot2.getLng();
        double dist = Math.sin(deg2rad(dot1.getLat())) * Math.sin(deg2rad(dot2.getLat())) +
                Math.cos(deg2rad(dot1.getLat())) * Math.cos(deg2rad(dot2.getLat())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;

        return dist; //단위 meter
    }


    private static double deg2rad(double deg) { // 10진수를 radian으로 변환
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {  // radian을 10진수로 변환
        return (rad * 180 / Math.PI);
    }

}
