package com.ssafy.home.dto;

import com.ssafy.home.dto.requestDto.NoticeRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private int noticeId;
    private String noticeTitle;
    private String adminId;
    private String noticeContent;
    private String postDate;

    public NoticeDto(NoticeRequest noticeRequest) {
        this.noticeTitle = noticeRequest.getNoticeTitle();
        this.noticeContent = noticeRequest.getNoticeContent();
    }
}
