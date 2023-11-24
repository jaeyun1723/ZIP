package com.ssafy.home.service;

import com.ssafy.home.dto.Page;
import com.ssafy.home.dto.Pagination;
import com.ssafy.home.dto.RatingDto;
import com.ssafy.home.dto.ReviewBoardDto;
import com.ssafy.home.dto.requestDto.AptReviewRequest;
import com.ssafy.home.dto.responseDto.PagingResponse;
import com.ssafy.home.repository.MemberMapper;
import com.ssafy.home.repository.RatingMapper;
import com.ssafy.home.repository.ReviewBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewBoardServiceImpl implements ReviewBoardService {

    private final ReviewBoardMapper reviewBoardMapper;
    private final MemberMapper memberMapper;
    private final RatingMapper ratingMapper;

    @Autowired
    public ReviewBoardServiceImpl(ReviewBoardMapper reviewBoardMapper, RatingMapper ratingMapper, MemberMapper memberMapper) {
        this.reviewBoardMapper = reviewBoardMapper;
        this.ratingMapper = ratingMapper;
        this.memberMapper = memberMapper;
    }

    @Override
    public void saveReviewBoard(ReviewBoardDto reviewBoardDto) throws Exception {
        reviewBoardMapper.saveReviewBoard(reviewBoardDto);
        RatingDto ratingDto = new RatingDto(reviewBoardDto);
        if (ratingMapper.isEmpty(reviewBoardDto.getAptCode()))
            ratingMapper.saveRating(ratingDto);
        else
            ratingMapper.addRating(ratingDto);
    }

    @Override
    public void updateReviewBoard(ReviewBoardDto reviewBoardDto) throws Exception {
        RatingDto ratingDto = new RatingDto(reviewBoardDto);
        // 평점 수정 전
        int score = reviewBoardMapper.getReview(reviewBoardDto.getBoardId()).getScore();
        // 평점 수정 후
        ratingDto.setTotalRating(reviewBoardDto.getScore() - score);
        reviewBoardMapper.updateReviewBoard(reviewBoardDto);
        ratingMapper.updateRating(ratingDto);
    }

    @Override
    public void deleteReviewBoard(int boardId) throws Exception {
        RatingDto ratingDto = new RatingDto(reviewBoardMapper.getReview(boardId));
        ratingMapper.deleteRating(ratingDto);
        reviewBoardMapper.deleteReviewBoard(boardId);
    }

    @Override
    public PagingResponse<ReviewBoardDto> getPageList(AptReviewRequest aptReviewRequest) throws Exception {
        Page page = new Page(aptReviewRequest.getPageRequest());
        int count = reviewBoardMapper.recordCount(aptReviewRequest.getAptCode());
        if (count < 1)
            return new PagingResponse<ReviewBoardDto>(Collections.emptyList(), null);
        Pagination pagination = new Pagination(count, page);
        page.setPagination(pagination);
        Map<String, Object> params = new HashMap<>();
        params.put("aptCode", aptReviewRequest.getAptCode());
        params.put("offset", page.getPagination().getOffset());
        params.put("limit", page.getRecordSize());
        List<ReviewBoardDto> list = reviewBoardMapper.getPageList(params);
        for (ReviewBoardDto r : list)
            r.setNickname(memberMapper.getMember(r.getMemberId()).getNickname());
        return new PagingResponse<ReviewBoardDto>(list, pagination);
    }

    @Override
    public ReviewBoardDto getReview(int boardId) throws Exception {
        return reviewBoardMapper.getReview(boardId);
    }

}
