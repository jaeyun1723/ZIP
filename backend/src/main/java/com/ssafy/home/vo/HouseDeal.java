package com.ssafy.home.vo;

import lombok.*;

@Data
public class HouseDeal {

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
