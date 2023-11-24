package com.ssafy.home.dto.requestDto;

import lombok.Data;

@Data
public class ReviewBoardRequest {

    private String title;
    private long aptCode;
    private String content;
    private int score;

}
