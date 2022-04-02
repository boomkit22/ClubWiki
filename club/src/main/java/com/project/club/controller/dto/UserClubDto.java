package com.project.club.controller.dto;

import com.project.club.domain.Club;
import lombok.Data;

@Data
public class UserClubDto {

    private Club club;
    private Long clubMemberInfoId;

    public UserClubDto(Club club, Long clubMemberInfoId) {
        this.club = club;
        this.clubMemberInfoId = clubMemberInfoId;
    }

}
