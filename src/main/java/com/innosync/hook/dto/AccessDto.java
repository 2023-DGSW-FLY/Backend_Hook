package com.innosync.hook.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class AccessDto {
    private Long id;
    private String name;
    private String stack;
    private String content;
    private String url;
    private String status = "matching";
    private LocalDateTime regDate, modDate;
}
