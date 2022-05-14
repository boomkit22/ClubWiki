package com.project.club.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClubBoardListDto{
    private Long wikiBoardId;
    private String wikiName;
}