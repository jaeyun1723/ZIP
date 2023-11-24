package com.ssafy.home.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    private long aptCode;
    private int reviewCount;
    private int totalRating;

    public RatingDto(ReviewBoardDto reviewBoardDto) {
        this.aptCode = reviewBoardDto.getAptCode();
        this.reviewCount = 1;
        this.totalRating = reviewBoardDto.getScore();
    }
}
