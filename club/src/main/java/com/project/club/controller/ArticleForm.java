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
    @NotEmpty(message = "제목은 필수입니다")
    private String title;

    private String data;
    private LocalDateTime writeTime;
}
