package com.project.club.api.dto;

import lombok.Data;

@Data
public class CreateClubBoardResponseDto {
    private String msg;

    public CreateClubBoardResponseDto(String msg) {
        this.msg = msg;
    }

}
