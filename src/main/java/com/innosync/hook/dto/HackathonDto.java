package com.innosync.hook.dto;

import com.innosync.hook.constant.Status;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

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

    private List<String> stack;

    private String url;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String writer;

    private String userName;

    private LocalDateTime regDate, modDate;
}
