package com.ssafy.home.controller;

import com.ssafy.home.dto.MemberDto;
import com.ssafy.home.dto.requestDto.LoginRequest;
import com.ssafy.home.dto.requestDto.MemberRequest;
import com.ssafy.home.exception.DuplicatedMemberException;
import com.ssafy.home.service.MemberService;
import com.ssafy.home.util.JWTUtil;
import io.swagger.annotations.Api;
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
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JWTUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/members/{memberId}")
    @ApiOperation(value = "회원 정보 불러오기 API")
    public ResponseEntity<?> getMember(@PathVariable String memberId, HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberId = jwtUtil.getMemberId(httpServletRequest.getHeader("Authorization"));
            MemberDto memberDto = memberService.getMember(memberId);
            resultMap.put("msg", "get Member Successfully");
            resultMap.put("object", memberDto);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);

        }
    }

    @PostMapping("/signup")
    @ApiOperation(value = "회원 가입 API, 회원가입 후 자동 로그인")
    public ResponseEntity<?> saveMember(@RequestBody MemberDto memberDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberService.existMember(memberDto.getMemberId());
            memberService.saveMember(memberDto);

            // 로그인
            String accessToken = jwtUtil.createAccessToken(memberDto.getMemberId());
            String refreshToken = jwtUtil.createRefreshToken(memberDto.getMemberId());
            memberService.saveRefreshToken(memberDto.getMemberId(), refreshToken);
            memberDto = memberService.getMember(memberDto.getMemberId());
            resultMap.put("memberInfo", memberDto);
            resultMap.put("access-token", accessToken);
            resultMap.put("refreshToken", refreshToken);
            resultMap.put("msg", "Member Save Successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
        } catch (DuplicatedMemberException d) {
            resultMap.put("msg", "ID already exists...");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(resultMap);
        }
    }

    @PatchMapping("/members/{memberId}")
    @ApiOperation(value = "회원 정보 수정 API")
    public ResponseEntity<?> updateMember(@PathVariable String memberId, @RequestBody MemberRequest memberRequest, HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            MemberDto memberDto = new MemberDto(memberRequest);
            memberId = jwtUtil.getMemberId(httpServletRequest.getHeader("Authorization"));
            memberDto.setMemberId(memberId);
            memberService.updateMember(memberDto);
            memberDto = memberService.getMember(memberId);
            resultMap.put("msg", "update Member Successfully");
            resultMap.put("object", memberDto);
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
        }
    }

    @DeleteMapping("/members/{memberId}")
    @ApiOperation(value = "회원 삭제 API")
    public ResponseEntity<?> deleteMember(@PathVariable String memberId, HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberId = jwtUtil.getMemberId(httpServletRequest.getHeader("Authorization"));
            memberService.deleteMember(memberId);
            resultMap.put("msg", "delete Member Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(resultMap);
        } catch (Exception e) {
            resultMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인 API")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.debug("login user : {}", loginRequest);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.ACCEPTED;
        try {
            MemberDto loginMember = memberService.loginMember(loginRequest);
            if (loginMember != null) {
                String accessToken = jwtUtil.createAccessToken(loginMember.getMemberId());
                String refreshToken = jwtUtil.createRefreshToken(loginMember.getMemberId());
                log.debug("access token : {}", accessToken);
                log.debug("refresh token : {}", refreshToken);

                //	발급받은 refresh token을 DB에 저장.
                memberService.saveRefreshToken(loginMember.getMemberId(), refreshToken);
                loginMember = memberService.getMember(loginMember.getMemberId());
                //	JSON으로 token 전달.
                resultMap.put("memberInfo", loginMember);
                resultMap.put("access-token", accessToken);
                resultMap.put("refreshToken", refreshToken);
                resultMap.put("msg", "login Successfully");
                httpStatus = HttpStatus.CREATED;
            } else {
                resultMap.put("msg", "Please check your username or password.");
                httpStatus = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            log.debug("login error : {}", e);
            resultMap.put("msg", e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, httpStatus);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃 API")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.ACCEPTED;
        try {
            String memberId = jwtUtil.getMemberId(httpServletRequest.getHeader("Authorization"));
            memberService.deleteRefreshToken(memberId);
            resultMap.put("msg", "Logout Success");
        } catch (Exception e) {
            log.debug("logout error : {}", e);
            resultMap.put("msg", e.getMessage());
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(resultMap, httpStatus);
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "refreshToken 재발급 API")
    public ResponseEntity<?> refreshToken(HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.ACCEPTED;
        String token = httpServletRequest.getHeader("Authorization");
        if (jwtUtil.checkToken(token)) {
            try {
                String memberId = jwtUtil.getMemberId(token);
                String accessToken = jwtUtil.createAccessToken(memberId);
                String refreshToken = jwtUtil.createRefreshToken(memberId);
                memberService.saveRefreshToken(memberId, refreshToken);
                log.debug("token : {}", accessToken);
                log.debug("Access token successfully reissued...");
                resultMap.put("msg", "Access token successfully reissued...");
                resultMap.put("access-token", accessToken);
                resultMap.put("refreshToken", refreshToken);
                httpStatus = HttpStatus.CREATED;

            } catch (Exception e) {
                log.debug("Unable to reissue access token.");
                resultMap.put("msg", "Unable to reissue access token. " + e.getMessage());
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            log.debug("Unable to use refresh token.");
            resultMap.put("msg", "Unable to use refresh token.");
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(resultMap, httpStatus);
    }
}
