package com.innosync.hook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
public class FCMResponseDto {
    private String content; // 내용 | ex) 양예성님이 지원하셨습니다.
    private String type; // m = 채팅 , p = 글
    private LocalDateTime regDate;
}
