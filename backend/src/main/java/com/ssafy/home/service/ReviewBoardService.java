package com.ssafy.home.service;

import com.ssafy.home.dto.ReviewBoardDto;
import com.ssafy.home.dto.requestDto.AptReviewRequest;
import com.ssafy.home.dto.responseDto.PagingResponse;

public interface ReviewBoardService {

    void saveReviewBoard(ReviewBoardDto reviewBoardDto) throws Exception;

    void updateReviewBoard(ReviewBoardDto reviewBoardDto) throws Exception;

    void deleteReviewBoard(int boardId) throws Exception;

    PagingResponse<ReviewBoardDto> getPageList(AptReviewRequest aptReviewRequest) throws Exception;

    ReviewBoardDto getReview(int boardId) throws Exception;
}

