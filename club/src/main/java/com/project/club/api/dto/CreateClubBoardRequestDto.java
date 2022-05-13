package com.project.club.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateClubBoardRequestDto {
    private String wikiName;
    private String wikiIntro;
    private String cpAnnouncement;
    private String boardCategory;
    private boolean isLock;


    @Builder
    public CreateClubBoardRequestDto(String wikiName, String wikiIntro, String cpAnnouncement, String boardCategory, boolean isLock) {
        this.wikiName = wikiName;
        this.wikiIntro = wikiIntro;
        this.cpAnnouncement = cpAnnouncement;
        this.boardCategory = boardCategory;
        this.isLock = isLock;
    }
}
