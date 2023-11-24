package com.ssafy.home.exception;

public class DuplicatedMemberException extends RuntimeException {
    public DuplicatedMemberException() {
        super("이미 존재하는 아이디입니다.");
    }
}