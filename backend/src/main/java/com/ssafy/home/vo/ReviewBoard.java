package com.ssafy.home.vo;

import lombok.*;

@Data
public class ReviewBoard {

    private int boardId;
    private String title;
    private long aptCode;
    private String memberId;
    private String content;
    private String registDate;
    private int score;
}
