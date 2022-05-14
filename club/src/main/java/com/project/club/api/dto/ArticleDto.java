package com.project.club.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ArticleDto{
    private String memberName;
    private String data;
    private List<ImageFileDto> imageFileList;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
