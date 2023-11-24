package com.ssafy.home.dto;

import com.ssafy.home.dto.requestDto.AptReviewRequest;
import com.ssafy.home.dto.requestDto.PageRequest;
import lombok.Data;

@Data
public class Page {
    private int page; // 현재 페이지
    private int recordSize; // 데이터 개수
    private int pageSize; // 하단에 출력할 페이지 번호 개수
    private Pagination pagination;

    public Page(int page, int recordSize, int pageSize) {
        this.page = page;
        this.recordSize = recordSize;
        this.pageSize = pageSize;
    }

    public Page(PageRequest pageRequest) {
        this.page = pageRequest.getPage();
        this.recordSize = pageRequest.getRecordSize();
        this.pageSize = pageRequest.getPageSize();
    }
}
