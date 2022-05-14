package com.project.club.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.club.api.dto.CreateClubBoardRequestDto;
import com.project.club.domain.Club;
import com.project.club.domain.ClubBoard;
import com.project.club.service.ClubBoardService;
import com.project.club.service.ClubService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClubBoardApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubBoardService clubBoardService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void testDown() throws Exception{
        clubBoardService.deleteAll();
        clubService.deleteAll();
    }

    @Test
    public void getPort()
    {
        assertThat(port).isGreaterThan(0);
    }

    @Test
    @WithMockUser
    public void ClubBoard_등록된다() throws Exception{
        //given
        String wikiName = "name";
        String wikiIntro = "intro";
        String cpAnnouncement = "oneLineReview";
        String boardCategory = "boardCategory";
        boolean isLock = false;
        CreateClubBoardRequestDto request = CreateClubBoardRequestDto.builder()
                .wikiName(wikiName)
                .wikiIntro(wikiIntro)
                .cpAnnouncement(cpAnnouncement)
                .boardCategory(boardCategory)
                .isLock(isLock)
                .build();
        Club club = new Club();
        club.setName("Test Club");
        Long clubId= clubService.join(club);
        String url = "http://localhost:8081" +   "/api/clubs/clubBoard/" + clubId.toString();
        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
        //then
        List<ClubBoard> all = clubBoardService.findAll();
        assertThat(all.get(0).getName()).isEqualTo(wikiName);
        assertThat(all.get(0).getIntro()).isEqualTo(wikiIntro);
        assertThat(all.get(0).getOneLineReview()).isEqualTo(cpAnnouncement);
        assertThat(all.get(0).getBoardCategory()).isEqualTo(boardCategory);
        assertThat(all.get(0).isBLock()).isEqualTo(isLock);
    }

    @Test
    @WithMockUser
    public void ClubBoard_조회된다() throws Exception {
        //given
        Club club = new Club();
        club.setName("Test Club");
        Long clubId= clubService.join(club);
        String name = "name";
        String intro = "intro";
        String oneLineReview = "oneLineReview";
        String boardCategory = "boardCategory";
        boolean bLock = false;
        ClubBoard clubBoard = ClubBoard.builder()
                .club(club)
                .name(name)
                .intro(intro)
                .oneLineReview(oneLineReview)
                .boardCategory(boardCategory)
                .bLock(bLock)
                .build();
        clubBoardService.join(clubBoard);
        String url = "http://localhost:8081" +   "/api/clubs/clubBoard/" + clubId.toString();
        //when, then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].wikiName").value(name));

    }
    
    //clubBoard 잠금, 해제

    @Test
    @WithMockUser
    public void ClubBoard_락언락() throws Exception{
        //given
        Club club = new Club();
        club.setName("Test Club");
        Long clubId= clubService.join(club);
        String name = "name";
        String intro = "intro";
        String oneLineReview = "oneLineReview";
        String boardCategory = "boardCategory";
        boolean bLock = false;
        ClubBoard clubBoard = ClubBoard.builder()
                .club(club)
                .name(name)
                .intro(intro)
                .oneLineReview(oneLineReview)
                .boardCategory(boardCategory)
                .bLock(bLock)
                .build();
        Long clubBoardId = clubBoardService.join(clubBoard);
        String url = "http://localhost:8081" +   "/api/clubs/clubBoard/lock/" + clubBoardId.toString();

        //when, then
        mvc.perform(post(url))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lock").value(true));

        mvc.perform(post(url))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lock").value(false));
        }




}
