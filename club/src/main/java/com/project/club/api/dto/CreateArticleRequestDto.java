package com.project.club.api.dto;

import com.project.club.domain.ImageFile;
import lombok.Data;

import java.util.List;

@Data
public class CreateArticleRequestDto {
    private String data;
    private List<ImageFileDto> imageFileList;
    public CreateArticleRequestDto()
    {

    }

    public CreateArticleRequestDto(String data, List<ImageFileDto> imageFileList) {
        this.data = data;
        this.imageFileList = imageFileList;
    }
}
