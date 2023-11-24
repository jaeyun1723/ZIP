package com.ssafy.home.dto;

import com.ssafy.home.dto.requestDto.MemberRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String memberId;
    private String pw;
    private String nickname;
    private String address;
    private boolean type;
    private String refreshToken;

    public MemberDto(MemberRequest memberRequest) {
        this.nickname = memberRequest.getNickname();
        this.address = memberRequest.getAddress();
    }
}
