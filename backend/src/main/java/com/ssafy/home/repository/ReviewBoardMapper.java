package com.ssafy.home.repository;

import com.ssafy.home.dto.ReviewBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewBoardMapper {

    void saveReviewBoard(ReviewBoardDto reviewBoardDto) throws Exception;

    void updateReviewBoard(ReviewBoardDto reviewBoardDto) throws Exception;

    void deleteReviewBoard(int boardId) throws Exception;

    List<ReviewBoardDto> getPageList(Map<String, Object> pageParams) throws Exception;

    int recordCount(long aptCode) throws Exception;

    ReviewBoardDto getReview(int boardId) throws Exception;
}