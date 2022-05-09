package com.project.club.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class ClubBoard{


    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String intro;

    private String oneLineReview;

    private String boardCategory;

    private boolean bLock;

    @OneToMany(mappedBy = "clubBoard", cascade = CascadeType.ALL)
    private List<Article> articleList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;


    public ClubBoard() {
    }

    @Builder
    public ClubBoard(String name, String intro, String oneLineReview, String boardCategory, boolean bLock, List<Article> articleList, Club club) {
        this.name = name;
        this.intro = intro;
        this.oneLineReview = oneLineReview;
        this.boardCategory = boardCategory;
        this.bLock = bLock;
        this.articleList = articleList;
        this.club = club;
    }
}
