package com.project.club.domain;


import com.project.club.controller.ArticleCategory;
import com.project.club.service.MemberService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Article extends BaseTimeEntity {


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

    private String data;

    //fetchType = Lazy (OneToMany 기본)
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ImageFile> imageFileList = new ArrayList<>();


    public void setImageFile(List<ImageFile> imageFileList) {
        imageFileList.forEach(iamgeFile -> iamgeFile.setArticle(this));
        this.imageFileList = imageFileList;
    }

    public Article(){

    }



    public Article(ClubBoard clubBoard, Member member, String data) {
        this.clubBoard = clubBoard;
        this.member = member;
        this.data = data;
    }

    @Builder
    public Article(ClubBoard clubBoard, Member member, String data, List<ImageFile> imageFileList) {
        this.clubBoard = clubBoard;
        this.member = member;
        this.data = data;
        this.imageFileList = imageFileList;
    }


}
