package com.innosync.hook.entity;

import com.innosync.hook.constant.Status;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "exersise")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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
    private String place;

    @Column
    private String writer;

    @Column
    private String userName;

    @Column
    private Long userId;

    @Column
    private String dateTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "exercise")
    private String exercise;

    @Builder
    public ExerciseEntity(String title, String content, String place, String writer, String userName, Long userId, String dateTime, Status status, String exercise) {
        this.title = title;
        this.content = content;
        this.place = place;
        this.writer = writer;
        this.userName = userName;
        this.userId = userId;
        this.dateTime = dateTime;
        this.status = status;
        this.exercise = exercise;
    }

    //    public void setExercise(String exercise) {
//        this.exercise = exercise;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setName(String name){
//        this.username = name;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public void setDateTime(String dateTime){
//        this.dateTime = dateTime;
//    }
//    public void setPlace(String place) {
//        this.place = place;
//    }

    @PrePersist
    private void prePersist() {
        if (status == null) {
            status = Status.matching;
        }
    }

    public void setStatusMatching() {
        this.status = Status.matching;
    }
    public void setStatusComplete() {
        this.status = Status.complete;
    }
}
