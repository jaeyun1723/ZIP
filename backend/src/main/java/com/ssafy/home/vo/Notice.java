package com.ssafy.home.vo;

import lombok.Data;

@Data
public class Notice {
    private int noticeId;
    private String noticeTitle;
    private String adminId;
    private String noticeContent;
    private String postDate;

}
