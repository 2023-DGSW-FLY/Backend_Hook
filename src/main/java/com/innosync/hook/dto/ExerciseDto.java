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
public class ExerciseDto {
    private Long id;
    private String title;
    private String content;
    private String place; // 장소
    private String dateTime; // 시간
    private String username; //작성자
    private Long userId; //작성자 구분번호 (FK)
    @Enumerated(EnumType.STRING)
    private Status status;
    private String exercise;
    private LocalDateTime regDate, modDate;

}
