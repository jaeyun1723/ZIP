package com.ssafy.home.service;

import com.ssafy.home.dto.MemberDto;
import com.ssafy.home.dto.requestDto.LoginRequest;
import com.ssafy.home.exception.DuplicatedMemberException;
import com.ssafy.home.exception.UnAuthorizedException;
import com.ssafy.home.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public void existMember(String memberId) throws Exception {
        if (!memberMapper.existMember(memberId))
            throw new DuplicatedMemberException();
    }

    @Override
    public void typeCheck(String memberId) throws Exception {
        if (!memberMapper.typeCheck(memberId))
            throw new UnAuthorizedException();
    }

    @Override
    public void saveMember(MemberDto memberDto) throws Exception {
        memberMapper.saveMember(memberDto);
    }

    @Override
    public MemberDto loginMember(LoginRequest loginRequest) throws Exception {
        return memberMapper.loginMember(loginRequest);
    }

    @Override
    public void saveRefreshToken(String memberId, String refreshToken) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("token", refreshToken);
        memberMapper.setRefreshToken(map);
    }

    @Override
    public MemberDto getMember(String memberId) throws Exception {
        return memberMapper.getMember(memberId);
    }

    @Override
    public void deleteRefreshToken(String memberId) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("token", null);
        memberMapper.setRefreshToken(map);
    }

    @Override
    public String getRefreshToken(String memberId) throws Exception {
        return memberMapper.getRefreshToken(memberId);
    }

    @Override
    public void updateMember(MemberDto memberDto) throws Exception {
        memberMapper.updateMember(memberDto);
    }

    @Override
    public void deleteMember(String memberId) throws Exception {
        memberMapper.deleteMember(memberId);
    }
}
