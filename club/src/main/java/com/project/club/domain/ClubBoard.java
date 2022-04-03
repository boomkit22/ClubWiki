package com.project.club.domain;

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

    @OneToMany(mappedBy = "clubBoard", cascade = CascadeType.ALL)
    private List<Article> articleList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;


    public ClubBoard() {
    }

    public ClubBoard(String name, List<Article> articleList) {
        this.name = name;
        this.articleList = articleList;
    }
}
