package com.ssafy.home.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super("계정 권한이 유효하지 않습니다.");
    }

}