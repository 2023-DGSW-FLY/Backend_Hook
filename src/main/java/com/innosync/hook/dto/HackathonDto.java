package com.innosync.hook.dto;

import com.innosync.hook.constant.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List; // Add this import for List

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class HackathonDto {
    private Long id;

    private String title;

    private String content;

    private List<String> stack; // Change the data type to List<String> for stack

    private String url;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime regDate, modDate;
}
