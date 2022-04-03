package com.project.club.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ClubBoardForm {

    @NotEmpty(message = "이름은 필수 입니다")
    private String name;
}
