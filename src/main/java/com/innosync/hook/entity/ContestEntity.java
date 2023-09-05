package com.innosync.hook.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "content")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class ContestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String imgUrl;

    @Column
    private String dateTime;

    @Column
    private Long timestamp;
}
