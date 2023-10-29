package com.innosync.hook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FCMEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content; // 내용 | ex) 양예성님이 지원하셨습니다.

    @Column
    private String type; // m = 채팅 , p = 글

    @Column
    private String userAccount;

    @Builder
    public FCMEntity(String content, String type, String userAccount) {
        this.content = content;
        this.type = type;
        this.userAccount = userAccount;
    }
}
