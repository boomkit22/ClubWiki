package com.project.club.api;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.hamcrest.Matchers;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void 게시글_등록된다() throws Exception{
        
    }
    @Test
    @WithMockUser
    public void 전체게시글_조회된다() throws Exception{
        //given
        Member member = new Member();
        member.setName("김정훈");
        member.setId(201820783L);
        memberService.join(member);

        Club club = new Club();
        club.setName("호완");
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].data").value(data))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].data").value(data2));
    }

    @Test
    @WithMockUser
    public void 한개게시글_조회된다() throws Exception{

        //given
        Member member = new Member();
        member.setName("김정훈");
        member.setId(201820783L);
        memberService.join(member);

        Club club = new Club();
        club.setName("호완");
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


}
