package com.project.club.repository;

import com.project.club.domain.ImageFile;
import com.project.club.respository.ImageFileRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageFileRepositoryTest {

    @Autowired
    ImageFileRepository imageFileRepository;

    @After
    public void cleanup()
    {
        imageFileRepository.deleteAll();
    }

    @Test
    public void 이미지저장_불러오기(){

        //given
        String filePath = "/abc/def";
        String fileName = "앵무새";
        String fileType = "jpg";
        Long fileSize = 256L;
        imageFileRepository.save(ImageFile.builder()
                .filePath(filePath)
                .fileName(fileName)
                .fileType(fileType)
                .fileSize(fileSize)
                .build()
        );
        //when
        List<ImageFile> imageFileList = imageFileRepository.findAll();
        ImageFile imageFile = imageFileList.get(0);

        //then
        assertThat(imageFile.getFileName()).isEqualTo(fileName);
        assertThat(imageFile.getFilePath()).isEqualTo(filePath);


    }
}
