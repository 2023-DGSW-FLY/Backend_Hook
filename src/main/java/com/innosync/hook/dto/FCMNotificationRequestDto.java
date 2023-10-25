package com.innosync.hook.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class FCMNotificationRequestDto {
    private Long targetUserId;
    private String title;
    private String body;

    @Builder
    public FCMNotificationRequestDto(Long targetUserId, String title, String body){
        this.targetUserId=targetUserId;
        this.title=title;
        this.body=body;
    }

}
