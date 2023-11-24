package com.ssafy.home.service;

import com.ssafy.home.dto.NoticeDto;
import com.ssafy.home.dto.requestDto.PageRequest;
import com.ssafy.home.dto.responseDto.PagingResponse;

public interface NoticeService {

    void saveNotice(NoticeDto noticeDto) throws Exception;

    NoticeDto getNotice(int noticeId) throws Exception;

    void updateNotice(NoticeDto noticeDto) throws Exception;

    void deleteNotice(int noticeId) throws Exception;

    PagingResponse<NoticeDto> getNoticeList(PageRequest pageRequest) throws Exception;
}

