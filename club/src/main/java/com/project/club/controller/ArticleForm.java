package com.project.club.controller;


import com.project.club.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleForm {

    private Member member;

    //제목
    @NotEmpty(message = "제목은 필수입니다")
    private String title;
    //소개글
    private String intro;
    //내용
    private String data;
    //카테고리
    private ArticleCategory articleCategory;
    //boolean 과 Boolean의 차이 : boolean은 null이 들어갈 수 없고
    //Boolean은 null이 들어갈 수 있음
    private boolean bLock;

    private String oneLineReview;

    private LocalDateTime writeTime;
}
