package com.ssafy.home.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LatLng {

    private double lat; // 위도
    private double lng; // 경도

    public LatLng(String lat, String lng) {
        this.lat = Double.parseDouble(lat);
        this.lng = Double.parseDouble(lng);
    }
}
