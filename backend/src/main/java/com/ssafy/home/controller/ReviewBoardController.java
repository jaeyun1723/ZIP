package com.ssafy.home.controller;

import com.ssafy.home.dto.ReviewBoardDto;
import com.ssafy.home.dto.requestDto.AptReviewRequest;
import com.ssafy.home.dto.requestDto.PageRequest;
import com.ssafy.home.dto.requestDto.ReviewBoardRequest;
import com.ssafy.home.dto.responseDto.PagingResponse;
import com.ssafy.home.service.ReviewBoardService;
import com.ssafy.home.util.JWTUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/review-board")
@Slf4j
public class ReviewBoardController {

    private final ReviewBoardService reviewBoardService;
    private final JWTUtil jwtUtil;

    @Autowired
    public ReviewBoardController(ReviewBoardService reviewBoardService, JWTUtil jwtUtil) {
        this.reviewBoardService = reviewBoardService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{boardId}")
    @ApiOperation(value = "아파트 후기 상세보기 API")
    public ResponseEntity<?> getReview(@PathVariable int boardId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            ReviewBoardDto reviewBoardDto = reviewBoardService.getReview(boardId);
            resultMap.put("msg", "detail Review Successfully");
            resultMap.put("object", reviewBoardDto);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @PostMapping("")
    @ApiOperation(value = "아파트 후기 등록 API")
    public ResponseEntity<?> saveReviewBoard(@RequestBody ReviewBoardRequest reviewBoardRequest, HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        ReviewBoardDto reviewBoardDto = new ReviewBoardDto(reviewBoardRequest);
        try {
            String memberId = jwtUtil.getMemberId(httpServletRequest.getHeader("Authorization"));
            reviewBoardDto.setMemberId(memberId);
            reviewBoardService.saveReviewBoard(reviewBoardDto);
            resultMap.put("msg", "save Review Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @PutMapping("/{boardId}")
    @ApiOperation(value = "아파트 후기 수정 API")
    public ResponseEntity<?> updateReviewBoard(@PathVariable int boardId,
                                               @RequestBody ReviewBoardRequest reviewBoardRequest, HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        ReviewBoardDto reviewBoardDto = new ReviewBoardDto(reviewBoardRequest);
        try {
            String memberId = jwtUtil.getMemberId(httpServletRequest.getHeader("Authorization"));
            reviewBoardDto.setMemberId(memberId);
            reviewBoardDto.setBoardId(boardId);
            reviewBoardService.updateReviewBoard(reviewBoardDto);
            resultMap.put("msg", "update Review Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @DeleteMapping("/{boardId}")
    @ApiOperation(value = "아파트 후기 삭제 API")
    public ResponseEntity<?> deleteReviewBoard(@PathVariable int boardId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            reviewBoardService.deleteReviewBoard(boardId);
            resultMap.put("msg", "delete Review Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @GetMapping("/page")
    @ApiOperation(value = "해당 아파트에 대한 후기 게시판 가져오기 API")
    public ResponseEntity<?> getReviewBoardList(@RequestParam(value = "aptCode") long aptCode,
                                                @ModelAttribute PageRequest pageRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            AptReviewRequest aptReviewRequest = new AptReviewRequest(aptCode, pageRequest);
            PagingResponse<ReviewBoardDto> pagingResponse = reviewBoardService.getPageList(aptReviewRequest);
            resultMap.put("msg", "get ReviewBoardList Successfully");
            resultMap.put("object", pagingResponse);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
        }
    }
}
