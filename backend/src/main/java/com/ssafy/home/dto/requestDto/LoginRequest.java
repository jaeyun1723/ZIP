package com.ssafy.home.dto.requestDto;

import lombok.Data;

@Data
public class LoginRequest {
    private String memberId;
    private String pw;
}
