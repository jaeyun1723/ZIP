package com.ssafy.home.dto;

import lombok.Data;

@Data
public class HouseDealDto {

    private long no;
    private String dealAmount;
    private int dealYear;
    private int dealMonth;
    private int dealDay;
    private String area;
    private String floor;
    private String cancelDealType;
    private long aptCode;
}
