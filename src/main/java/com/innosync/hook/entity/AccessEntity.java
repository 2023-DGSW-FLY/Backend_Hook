package com.innosync.hook.entity;

import com.innosync.hook.constant.AccessStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "access")
@Builder
@AllArgsConstructor
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
    private AccessStatus status;

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeUrl(String url) {
        this.url = url;
    }

    @PrePersist
    private void prePersist() {
        if (status == null) {
            status = AccessStatus.matching;
        }
    }
    public void changeMatching(){
        this.status = AccessStatus.matching;
    }
    public void changeComplete(){
        this.status = AccessStatus.complete;
    }
}
