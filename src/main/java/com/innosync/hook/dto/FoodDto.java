package com.innosync.hook.dto;

import com.innosync.hook.constant.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

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
    private String foodName; // 음식명
    @Enumerated(EnumType.STRING)
    private Status status;
    private String writer;
    private String userName;
    private Long userId; // 작성자 FK값
    private LocalDateTime regDate, modDate;

}
