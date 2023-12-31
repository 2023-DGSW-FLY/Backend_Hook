package com.innosync.hook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "hackathon_support")
@Getter
@Setter
public class SupportEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id")
    private HackathonEntity hackathon;

    private String applicantName;
    private String studentId;
    private String contact;
    private String introduction;
    private String portfolioLink;
    private Long userId;

}
