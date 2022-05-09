package com.project.club.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ImageFile {

    @Id
    @GeneratedValue
    private Long id;

    private String filePath;

    private String fileName;

    private String fileType;

    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public ImageFile()
    {

    }

    @Builder
    public ImageFile(String filePath, String fileName, String fileType, Long fileSize) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
