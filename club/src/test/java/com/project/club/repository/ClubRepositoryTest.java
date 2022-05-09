package com.project.club.repository;

import com.project.club.domain.Club;
import com.project.club.respository.ClubRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClubRepositoryTest {

    @Autowired
    ClubRepository clubRepository;

    @After
    public void cleanup() { clubRepository.deleteAll();}


    @Test
    public void 클럽저장_불러오기(){
        //given
        String name = "호완";
        String introduction = "복싱동아리";
        String location = "구학생회관210호";
        String phoneNumber = "01063559320";
        String image_url = "/abc/def";
        clubRepository.save(Club.builder()
                .name(name)
                .introduction(introduction)
                .location(location)
                .phoneNumber(phoneNumber)
                .image_url(image_url)
                .build()
        );

        //when
        List<Club> clubList = clubRepository.findAll();
        Club club = clubList.get(0);

        //then
        assertThat(club.getName()).isEqualTo(name);
        assertThat(club.getIntroduction()).isEqualTo(introduction);
        assertThat(club.getLocation()).isEqualTo(location);
        assertThat(club.getImage_url()).isEqualTo(image_url);

    }

    @Test
    public void 클럽BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.now();

        String name = "호완";
        String introduction = "복싱동아리";
        String location = "구학생회관210호";
        String phoneNumber = "01063559320";
        String image_url = "/abc/def";
        clubRepository.save(Club.builder()
                .name(name)
                .introduction(introduction)
                .location(location)
                .phoneNumber(phoneNumber)
                .image_url(image_url)
                .build()
        );

        //when
        List<Club> clubList = clubRepository.findAll();
        Club club = clubList.get(0);

        //then
        assertThat(club.getCreatedDate()).isAfter(now);
        assertThat(club.getModifiedDate()).isAfter(now);

    }
}
