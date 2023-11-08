package com.innosync.hook.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ContestDto {

    private Long id;

    private String title; //대회 제목

    private String imgUrl; //대회 이미지

    private String dateTime; //대회 일시

    private String url; // 대회 url

}
