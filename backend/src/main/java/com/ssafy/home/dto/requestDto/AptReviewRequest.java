package com.ssafy.home.dto.requestDto;

import lombok.*;

@Data
@AllArgsConstructor
public class AptReviewRequest {
    private long aptCode;
    private PageRequest pageRequest;
}
