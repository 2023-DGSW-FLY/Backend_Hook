package com.innosync.hook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innosync.hook.constant.Status;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hackathon")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class HackathonEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column
    private String stack;

    @Column
    private String url;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private String writer;

    @Column
    private String userName;

    @Column
    private Long userId;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id") // 사용자 정보를 참조하는 외래 키
//    private User user;

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // 스택 오버플로우 방지
    private List<SupportEntity> supports = new ArrayList<>();


    // 게시물과 지원서 연결
    public void addSupport(SupportEntity support) {
        supports.add(support);
        support.setHackathon(this);
    }


    public void setWriter(String username){
        this.writer = username;
    }

    public void setUserName(String name){
        this.userName=name;
    }
    public void setUserId(Long id){
        this.userId=id;
    }
    public void hackathonChangeTitle(String title){
        this.title = title;
    }
    public void hackathonChangeContent(String content) {
        this.content = content;
    }
    public void hackathonChangeStack(List<String> stack){
        this.stack = String.join(",", stack);;
    }
    public void hackathonChangeUrl(String url) {
        this.url = url;
    }

    @PrePersist
    private void prePersist() {
        if (status == null) {
            status = Status.matching;
        }
    }
    public void hackathonChangeMatching(){
        this.status = Status.matching;
    }
    public void hackathonChangeComplete(){
        this.status = Status.complete;
    }
}
