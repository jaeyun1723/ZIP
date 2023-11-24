package com.ssafy.home.service;

import com.ssafy.home.dto.MemberDto;
import com.ssafy.home.dto.requestDto.LoginRequest;

public interface MemberService {

    void existMember(String memberId) throws Exception;
    void typeCheck(String memberId) throws Exception;

    void saveMember(MemberDto memberDto) throws Exception;

    MemberDto loginMember(LoginRequest loginRequest) throws Exception;

    void saveRefreshToken(String memberId, String refreshToken) throws Exception;

    MemberDto getMember(String memberId) throws Exception;

    void deleteRefreshToken(String memberId) throws Exception;

    String getRefreshToken(String memberId) throws Exception;

    void updateMember(MemberDto memberDto) throws Exception;

    void deleteMember(String memberId) throws Exception;
}
