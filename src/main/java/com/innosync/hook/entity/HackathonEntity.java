package com.innosync.hook.entity;

import com.innosync.hook.constant.Status;
import jakarta.persistence.*;
import lombok.*;

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
