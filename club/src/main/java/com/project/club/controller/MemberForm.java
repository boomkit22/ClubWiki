package com.project.club.controller;

import com.project.club.Interest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class MemberForm {

    @NotNull(message = "학번은 필수 입니다")
    private Long id;
    @NotEmpty(message = "이름은 필수 입니다")
    private String name;
    @NotEmpty(message = "학과는 필수 입니다")
    private String department;
    @NotEmpty(message = "이메일은 필수 입니다")
    private String email;
    private List<Interest> interestList;
    private boolean bReceiveMail;
    private String role = "Member";
}
