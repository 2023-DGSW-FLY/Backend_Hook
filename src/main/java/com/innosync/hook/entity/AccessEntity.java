package com.innosync.hook.entity;

import com.innosync.hook.constant.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "access")
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class AccessEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column
    private String stack;
    @Column(length = 1000, nullable = false)
    private String content;
    @Column(length = 100)
    private String url;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column
    private Long userId;

    @Builder
    public AccessEntity(String name, String stack, String content, String url, Status status, Long userId) {
        this.name = name;
        this.stack = stack;
        this.content = content;
        this.url = url;
        this.status = status;
        this.userId = userId;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeStack(String stack){
        this.stack = stack;
    }
    public void changeUrl(String url) {
        this.url = url;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUserId(Long id){
        this.userId=id;
    }

    @PrePersist
    private void prePersist() {
        if (status == null) {
            status = Status.matching;
        }
    }
    public void changeMatching(){
        this.status = Status.matching;
    }
    public void changeComplete(){
        this.status = Status.complete;
    }
}
