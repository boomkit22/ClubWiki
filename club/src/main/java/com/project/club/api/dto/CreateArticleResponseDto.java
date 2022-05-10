package com.project.club.api.dto;

import com.project.club.domain.ImageFile;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateArticleResponseDto {
    private Long id;
    private String data;
    private int imageNumber;

    private List<ImageFileDto> imageFileList;

    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    @Builder
    public CreateArticleResponseDto(Long id, String data, List<ImageFileDto> imageFileList, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.data = data;
        this.imageNumber = imageFileList.size();
        this.imageFileList = imageFileList;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }

}
