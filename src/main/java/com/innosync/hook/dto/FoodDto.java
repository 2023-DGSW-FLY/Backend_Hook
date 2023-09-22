package com.innosync.hook.dto;

import com.innosync.hook.constant.Status;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class FoodDto {
    private Long id;
    private String title;
    private String content;
    private String place; // 장소
    private String dateTime; // 시간
    @Enumerated(EnumType.STRING)
    private Status status;
    private String writer;
    private Long userId; // 작성자 FK값
    private LocalDateTime regDate, modDate;

}
