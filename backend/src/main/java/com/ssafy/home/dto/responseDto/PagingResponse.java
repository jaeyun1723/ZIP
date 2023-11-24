package com.ssafy.home.dto.responseDto;

import com.ssafy.home.dto.Pagination;
import lombok.Data;

import java.util.List;

@Data
public class PagingResponse<T> {
    private List<T> pageList;
    private Pagination pagination;

    public PagingResponse(List<T> pageList, Pagination pagination) {
        this.pageList = pageList;
        this.pagination = pagination;
    }
}
