package com.ssafy.home.dto;

import lombok.Data;

@Data
public class Pagination {
    private int totalRecordCount; // 전체 데이터 수
    private int totalPageCount; // 전체 페이지 수
    private int startPage; // 하단에 보여줄 첫 페이지 번호
    private int endPage; // 하단에 보여줄 끝 페이지 번호
    private int offset; // Limit 시작 위치(offset)
    private boolean prevPage; // 이전 페이지 존재 여부(현 페이지네이션 기준)
    private boolean nextPage; // 다음 페이지 존재 여부(현 페이지네이션 기준)

    public Pagination(int totalRecordCount, Page page) {
        // 쿼리문 실행 결과 총 데이터 개수
        this.totalRecordCount = totalRecordCount;
        // 전체 페이지 수 계산
        this.totalPageCount = (int) (Math.ceil((double) totalRecordCount / page.getRecordSize()));
        if (page.getPage() > totalPageCount)
            page.setPage(totalPageCount);
        // 현재 페이지네이션의 첫 페이지 번호
        this.startPage=((page.getPage() - 1) / page.getPageSize()) * page.getPageSize() + 1;
        // 현재 페이지네이션의 끝 페이지 번호
        this.endPage = Math.min(startPage + page.getPageSize() - 1, totalPageCount);
        // offset, limit 시작 위치 계산
        this.offset = (page.getPage() - 1) * page.getRecordSize();
        // 이전 페이지 존재 여부
        this.prevPage = startPage != 1;
        // 다음 페이지 존재 여부
        this.nextPage = (this.endPage * page.getRecordSize()) < totalRecordCount;
    }

}
