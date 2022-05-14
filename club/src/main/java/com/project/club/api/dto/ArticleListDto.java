package com.project.club.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ArticleListDto{
    private Long articleId;
    private String text;
    private Long imageCount;
    private List<ImageFileDto> imageFileList;
    private LocalDateTime createdDate;
}
