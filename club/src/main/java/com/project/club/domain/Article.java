package com.project.club.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private ClubBoard clubBoard;

    //작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Member member;

    private String title;

    private String data;

    private LocalDateTime writeTime;


    public Article(){


    }


    public Article(ClubBoard clubBoard, Member member, String data, LocalDateTime writeTime) {
        this.clubBoard = clubBoard;
        this.member = member;
        this.data = data;
        this.writeTime = writeTime;
    }


}
