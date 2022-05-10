package com.project.club.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateClubBoardRequestDto {
    private String name;
    private String intro;
    private String oneLineReview;
    private String boardCategory;
    private boolean bLock;



    @Builder
    public CreateClubBoardRequestDto(String name, String intro, String oneLineReview, String boardCategory, boolean bLock) {
        this.name = name;
        this.intro = intro;
        this.oneLineReview = oneLineReview;
        this.boardCategory = boardCategory;
        this.bLock = bLock;
    }
}
