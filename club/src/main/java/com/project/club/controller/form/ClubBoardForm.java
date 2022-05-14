package com.project.club.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ClubBoardForm {

    @NotEmpty(message = "이름은 필수 입니다")
    private String name;

    private String intro;

    private String oneLineReview;

    private String boardCategory;

    private boolean bLock;
}
