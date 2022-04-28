package com.project.club.domain;


import com.project.club.controller.ArticleCategory;
import lombok.Builder;
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

    private String intro;

    private String data;

    private String articleCategory;


    private boolean bLock;


    private String oneLineReview;

    private LocalDateTime writeTime;

    public Article(){

    }


    public Article(ClubBoard clubBoard, Member member, String title, String intro, String data, String articleCategory, boolean bLock, String oneLineReview, LocalDateTime writeTime) {
        this.clubBoard = clubBoard;
        this.member = member;
        this.title = title;
        this.intro = intro;
        this.data = data;
        this.articleCategory = articleCategory;
        this.bLock = bLock;
        this.oneLineReview = oneLineReview;
        this.writeTime = writeTime;
    }


}
