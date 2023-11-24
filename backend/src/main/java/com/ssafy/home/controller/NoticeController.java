package com.ssafy.home.controller;

import com.ssafy.home.dto.NoticeDto;
import com.ssafy.home.dto.requestDto.NoticeRequest;
import com.ssafy.home.dto.requestDto.PageRequest;
import com.ssafy.home.dto.responseDto.PagingResponse;
import com.ssafy.home.exception.UnAuthorizedException;
import com.ssafy.home.service.MemberService;
import com.ssafy.home.service.NoticeService;
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
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @Autowired
    public NoticeController(NoticeService noticeService, MemberService memberService, JWTUtil jwtUtil) {
        this.noticeService = noticeService;
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("")
    @ApiOperation(value = "공지사항 등록 API")
    public ResponseEntity<?> saveNotice(@RequestBody NoticeRequest noticeRequest, HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            NoticeDto noticeDto = new NoticeDto(noticeRequest);
            String memberId = jwtUtil.getMemberId(httpServletRequest.getHeader("Authorization"));
            memberService.typeCheck(memberId);
            noticeDto.setAdminId(memberId);
            noticeService.saveNotice(noticeDto);
            resultMap.put("msg", "save Notice Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @GetMapping("/detail/{noticeId}")
    @ApiOperation(value = "공지사항 상세보기 API")
    public ResponseEntity<?> getNotice(@PathVariable int noticeId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            NoticeDto noticeDto = noticeService.getNotice(noticeId);
            resultMap.put("msg", "get Notice Successfully");
            resultMap.put("object", noticeDto);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @PutMapping("/{noticeId}")
    @ApiOperation(value = "공지사항 수정 API")
    public ResponseEntity<?> updateNotice(@PathVariable int noticeId,
                                          @RequestBody NoticeRequest noticeRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            NoticeDto noticeDto = new NoticeDto(noticeRequest);
            noticeDto.setNoticeId(noticeId);
            noticeService.updateNotice(noticeDto);
            resultMap.put("msg", "update Notice Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @DeleteMapping("/{noticeId}")
    @ApiOperation(value = "공지사항 삭제 API")
    public ResponseEntity<?> deleteNotice(@PathVariable int noticeId, HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String memberId = jwtUtil.getMemberId(httpServletRequest.getHeader("Authorization"));
            memberService.typeCheck(memberId);
            noticeService.deleteNotice(noticeId);
            resultMap.put("msg", "delete Notice Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(resultMap);
        }
    }

    @GetMapping("/board")
    @ApiOperation(value = "공지사항 게시판 불러오기 API")
    public ResponseEntity<?> getNoticeList(@ModelAttribute PageRequest pageRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            PagingResponse<NoticeDto> pagingResponse = noticeService.getNoticeList(pageRequest);
            resultMap.put("msg", "get NoticeList Successfully");
            resultMap.put("object", pagingResponse);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
        }
    }
}
