package com.ssafy.home.service;

import com.ssafy.home.dto.*;
import com.ssafy.home.dto.requestDto.PageRequest;
import com.ssafy.home.dto.responseDto.PagingResponse;
import com.ssafy.home.repository.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Override
    public void saveNotice(NoticeDto noticeDto) throws Exception {
        noticeMapper.saveNotice(noticeDto);
    }

    @Override
    public NoticeDto getNotice(int noticeId) throws Exception {
        return noticeMapper.getNotice(noticeId);
    }

    @Override
    public void updateNotice(NoticeDto noticeDto) throws Exception {
        noticeMapper.updateNotice(noticeDto);
    }

    @Override
    public void deleteNotice(int noticeId) throws Exception {
        noticeMapper.deleteNotice(noticeId);
    }

    @Override
    public PagingResponse<NoticeDto> getNoticeList(PageRequest pageRequest) throws Exception {
        Page page = new Page(pageRequest);
        int count = noticeMapper.noticeCount();
        if (count < 1)
            return new PagingResponse<NoticeDto>(Collections.emptyList(), null);
        Pagination pagination = new Pagination(count, page);
        page.setPagination(pagination);
        Map<String, Integer> params = new HashMap<>();
        params.put("offset", page.getPagination().getOffset());
        params.put("limit", page.getRecordSize());
        List<NoticeDto> list = noticeMapper.getNoticeList(params);
        return new PagingResponse<NoticeDto>(list, pagination);
    }
}
