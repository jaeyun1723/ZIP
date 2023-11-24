package com.ssafy.home.repository;

import com.ssafy.home.dto.MemberDto;
import com.ssafy.home.dto.requestDto.LoginRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberMapper {
    boolean existMember(String memberId) throws Exception;

    boolean typeCheck(String memberId) throws Exception;

    void saveMember(MemberDto memberDto) throws Exception;

    MemberDto loginMember(LoginRequest loginRequest) throws Exception;

    void setRefreshToken(Map<String, String> map) throws Exception;

    MemberDto getMember(String memberId) throws Exception;

    String getRefreshToken(String memberId) throws Exception;

    void updateMember(MemberDto memberDto) throws Exception;

    void deleteMember(String memberId) throws Exception;
}
