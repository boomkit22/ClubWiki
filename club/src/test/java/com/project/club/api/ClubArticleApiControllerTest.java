package com.project.club.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.club.api.dto.CreateArticleRequestDto;
import com.project.club.api.dto.ImageFileDto;
import com.project.club.domain.*;
import com.project.club.service.ArticleService;
import com.project.club.service.ClubBoardService;
import com.project.club.service.ClubService;
import com.project.club.service.MemberService;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import org.hamcrest.Matchers;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClubArticleApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClubService clubService;
    @Autowired
    private MemberService memberService;

    @Autowired
    private ArticleService articleService;

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
        articleService.deleteAll();
        memberService.deleteAll();

    }

    @Test
    public void getPort()
    {
        assertThat(port).isGreaterThan(0);
    }


    @Test
    @WithMockUser
    public void ?????????_????????????() throws Exception{
        //given
        Member member = new Member();
        member.setName("?????????");
        member.setId(201820783L);
        memberService.join(member);

        Club club = new Club();
        club.setName("??????");
        clubService.join(club);
        ClubBoard clubBoard = new ClubBoard();
        clubBoard.setClub(club);

        Long clubBoardId = clubBoardService.join(clubBoard);

        ImageFile imageFile = ImageFile.builder()
                .fileName("abc")
                .fileSize(256L)
                .fileType("jpg")
                .filePath("/abc")
                .build();
        List<ImageFile> imageFileList = new ArrayList<>();
        imageFileList.add(imageFile);

        List<ImageFileDto> imageFileDtoList = imageFileList.stream().map(m-> ImageFileDto.builder()
                .fileName(m.getFileName())
                .filePath(m.getFilePath())
                .fileType(m.getFileType())
                .fileSize(m.getFileSize())
                .build()).collect(Collectors.toList());


        String data = "TEST";

        String url = "http://localhost:8081" +   "/api/clubs/articles/" + clubBoardId.toString();

        CreateArticleRequestDto requestDto = new CreateArticleRequestDto(data, imageFileDtoList);
        //when, then
        LocalDateTime now = LocalDateTime.now();

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .param("memberId", member.getId().toString() )
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.memberId").value(member.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.memberName").value(member.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(data));

        List<Article> articleList = articleService.findByClubBoard(clubBoard);

        Article postArticle = articleList.get(0);


        assertThat(postArticle.getData()).isEqualTo(data);
        assertThat(postArticle.getCreatedDate()).isAfter(now);

    }
    @Test
    @WithMockUser
    public void ???????????????_????????????() throws Exception{
        //given
        Member member = new Member();
        member.setName("?????????");
        member.setId(201820783L);
        memberService.join(member);

        Club club = new Club();
        club.setName("??????");
        clubService.join(club);
        ClubBoard clubBoard = new ClubBoard();
        clubBoard.setClub(club);
        Long clubBoardId = clubBoardService.join(clubBoard);

        ImageFile imageFile = ImageFile.builder()
                .fileName("abc")
                .fileSize(256L)
                .fileType("jpg")
                .filePath("/abc")
                .build();

        List<ImageFile> imageFileList = new ArrayList<>();
        imageFileList.add(imageFile);

        ImageFile imageFile2 = ImageFile.builder()
                .fileName("abc")
                .fileSize(256L)
                .fileType("jpg")
                .filePath("/abc")
                .build();


        List<ImageFile> imageFileList2 = new ArrayList<>();
        imageFileList2.add(imageFile2);

        String data = "TEST";


        Article article = Article.builder()
                .clubBoard(clubBoard)
                .member(member)
                .data(data)
                .build();

        article.setImageFile(imageFileList);

        String data2 = "TEST2";

        Article article2 = Article.builder()
                .clubBoard(clubBoard)
                .member(member)
                .data(data2)
                .build();
        article2.setImageFile(imageFileList2);

        articleService.join(article);
        articleService.join(article2);
        String url = "http://localhost:8081" +   "/api/clubs/allArticles/" + clubBoardId.toString();

        //when ,then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleCount").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleList[0].text").value(data))
                .andExpect(MockMvcResultMatchers.jsonPath("$.articleList[1].text").value(data2));
    }

    @Test
    @WithMockUser
    public void ???????????????_????????????() throws Exception{

        //given
        Member member = new Member();
        member.setName("?????????");
        member.setId(201820783L);
        memberService.join(member);

        Club club = new Club();
        club.setName("??????");
        clubService.join(club);
        ClubBoard clubBoard = new ClubBoard();
        clubBoard.setClub(club);
        clubBoardService.join(clubBoard);


        ImageFile imageFile = ImageFile.builder()
                .fileName("abc")
                .fileSize(256L)
                .fileType("jpg")
                .filePath("/abc")
                .build();


        List<ImageFile> imageFileList = new ArrayList<>();

        imageFileList.add(imageFile);

        String data = "TEST";


        Article article = Article.builder()
                .clubBoard(clubBoard)
                .member(member)
                .data(data)
                .build();
        article.setImageFile(imageFileList);

        Long articleId = articleService.join(article);

        String url = "http://localhost:8081" +   "/api/clubs/articles/" + articleId.toString();

        //when,then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.memberName").value(member.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(data))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageFileList[0].fileName").value("abc"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate", Matchers.greaterThanOrEqualTo(article.getCreatedDate())));
    }

    //todo
    //????????? ??????
    //????????? ??????


}
