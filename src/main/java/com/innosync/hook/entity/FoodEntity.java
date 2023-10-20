package com.innosync.hook.entity;

import com.innosync.hook.constant.Status;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "food")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class FoodEntity extends BaseEntity{
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
    private String foodName;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private String writer;

    @Column
    private String userName;


    @Column
    private Long userId; // 작성자 FK값

    @Builder
    public FoodEntity(String title, String content, String place, String foodName, Status status, String writer, String userName, Long userId) {
        this.title = title;
        this.content = content;
        this.place = place;
        this.foodName = foodName;
        this.status = status;
        this.writer = writer;
        this.userName = userName;
        this.userId = userId;
    }



//    public void setDateTime(String dateTime) {
//        this.dateTime = dateTime;
//    }
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
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

//    public void setWriter(String username){
//        this.writer = username;
//    }
    public void setStatusMatching() {
        this.status = Status.matching;
    }
    public void setStatusComplete() {
        this.status = Status.complete;
    }
}

