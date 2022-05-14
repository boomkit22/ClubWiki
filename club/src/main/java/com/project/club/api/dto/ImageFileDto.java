package com.project.club.api.dto;

import com.project.club.domain.ImageFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageFileDto {
    private String filePath;
    private String fileName;
    private String fileType;
    private Long fileSize;

    @Builder
    public ImageFileDto(String filePath, String fileName, String fileType, Long fileSize) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public ImageFileDto(ImageFile imageFile){
        this.filePath = imageFile.getFilePath();
        this.fileName = imageFile.getFileName();
        this.fileType = imageFile.getFileType();
        this.fileSize = imageFile.getFileSize();
    }

}