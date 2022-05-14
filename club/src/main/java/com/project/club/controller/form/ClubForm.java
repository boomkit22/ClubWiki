package com.project.club.controller.form;

import com.project.club.domain.ClubInterest;
import com.project.club.domain.StudentInterest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class ClubForm {

    @NotEmpty(message = "이름은 필수 입니다")
    private String name;
    private List<ClubInterest> interestList;

}
