package com.ssafy.home.dto;

import com.ssafy.home.dto.requestDto.ReviewBoardRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewBoardDto {

    private int boardId;
    private String title;
    private long aptCode;
    private String memberId;
    private String nickname;
    private String content;
    private String registDate;
    private int score;

    public ReviewBoardDto(ReviewBoardRequest reviewBoardRequest) {
        this.title = reviewBoardRequest.getTitle();
        this.aptCode = reviewBoardRequest.getAptCode();
        this.content = reviewBoardRequest.getContent();
        this.score = reviewBoardRequest.getScore();
    }

}
