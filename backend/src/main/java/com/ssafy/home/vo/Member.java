package com.ssafy.home.vo;

import lombok.*;

@Data
public class Member {

    private String memberId;
    private String pw;
    private String nickname;
    private String address;
    private boolean type;
    private String refreshToken;

}
