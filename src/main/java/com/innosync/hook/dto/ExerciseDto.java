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
public class ExerciseDto {
    private Long id;
    private String title;
    private String content;
    private String place; // 장소
    private String dateTime; // 시간
    private String writer; //작성자 아이디
    private String userName; //작성자 실명
    private Long userId; //작성자 구분번호 (FK)
    @Enumerated(EnumType.STRING)
    private Status status;
    private String exercise;
    private LocalDateTime regDate, modDate;

}
