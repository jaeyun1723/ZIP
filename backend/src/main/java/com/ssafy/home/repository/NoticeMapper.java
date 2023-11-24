package com.ssafy.home.repository;

import com.ssafy.home.dto.NoticeDto;
import com.ssafy.home.dto.ReviewBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {


    void saveNotice(NoticeDto noticeDto) throws Exception;

    NoticeDto getNotice(int noticeId) throws Exception;

    void updateNotice(NoticeDto noticeDto) throws Exception;

    void deleteNotice(int noticeId) throws Exception;

    List<NoticeDto> getNoticeList(Map<String, Integer> params) throws Exception;

    int noticeCount() throws Exception;
}