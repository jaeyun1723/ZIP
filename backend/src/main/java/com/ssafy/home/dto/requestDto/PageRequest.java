package com.ssafy.home.dto.requestDto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Data
public class PageRequest {
    @Value("${pageinfo.page}")
    private int page; // 현재 페이지
    @Value("${pageinfo.recordsize}")
    private int recordSize; // 데이터 개수
    @Value("${pageinfo.pagesize}")
    private int pageSize; // 하단에 출력할 페이지 번호 개수

    public PageRequest() {
        this.page = 1;
        this.recordSize = 3;
        this.pageSize = 4;
    }
}
