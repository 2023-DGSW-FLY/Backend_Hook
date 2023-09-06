package com.innosync.hook.entity;

import com.innosync.hook.constant.Status;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "exersise")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class ExerciseEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column
    private String location;

    @Column
    private String dateTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @PrePersist
    private void prePersist() {
        if (status == null) {
            status = Status.matching;
        }
    }

    public void setStatusMatching(Status status) {
        this.status = Status.matching;
    }
    public void setStatusComplete(Status status) {
        this.status = Status.complete;
    }
}
