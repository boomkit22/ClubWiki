package com.project.club.api;

//todo
//clubBoard로 전체 article
//article 생성
//article 읽기
//article 수정

import com.project.club.controller.ArticleCategory;
import com.project.club.domain.Article;
import com.project.club.domain.ClubBoard;
import com.project.club.domain.Member;
import com.project.club.service.ArticleService;
import com.project.club.service.ClubBoardService;
import com.project.club.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

//잠금,해제 api
@RestController
@RequiredArgsConstructor
public class ClubArticleApiController {

    private final ArticleService articleService;
    private final ClubBoardService clubBoardService;
    private final MemberService memberService;

    @GetMapping("/api/clubs/allArticles/{clubBoardId}")
    public Result allArticleList(@PathVariable("clubBoardId") Long clubBoardId)
    {
        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);
        List<Article> articleList = articleService.findByClubBoard(clubBoard);
        List<ArticleListDto> collect = articleList.stream().map(m-> new ArticleListDto(m.getTitle(),m.getMember().getName(),
                m.getWriteTime())).collect(Collectors.toList());

        return new Result(collect.size(),collect);
    }

    @GetMapping("/api/clubs/lockedArticles/{clubBoardId}")
    public Result lockedArticleList(@PathVariable("clubBoardId") Long clubBoardId
                                    ,@RequestParam(name="locked") int locked)
    {
        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);
        List<Article> articleList = articleService.findByClubBoard(clubBoard);

        if(locked == 0 )
        {
            articleList.removeIf(article -> (article.isBLock() == true));
        }
        else if(locked == 1)
        {
            articleList.removeIf(article -> (article.isBLock() == false));
        }
        List<ArticleListDto> collect = articleList.stream().map(m-> new ArticleListDto(m.getTitle(),m.getMember().getName(),
                m.getWriteTime())).collect(Collectors.toList());

        return new Result(collect.size(),collect);
    }




    @GetMapping("/api/clubs/articles/{articleId}")
    public ArticleDto article(@PathVariable("articleId") Long articleId)
    {
        Article article = articleService.findById(articleId);
        ArticleDto articleDto = new ArticleDto(article.getMember().getName()
        ,article.getTitle(),article.getIntro(),article.getData()
        ,article.getArticleCategory()
        ,article.isBLock(),article.getOneLineReview(),article.getWriteTime());

        return articleDto;
    }

    @PostMapping("/api/clubs/articles/{clubBoardId}")
    public CreateArticleResponse saveArticle(@PathVariable("clubBoardId") Long clubBoardId,
                                             @RequestParam(name="memberId") Long memberId,
                                             @RequestBody @Valid CreateArticleRequest request)
    {
        Article article = new Article();
        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);

        Member member = memberService.findOne(memberId);

        article.setClubBoard(clubBoard);
        article.setMember(member);
        article.setTitle(request.getTitle());
        article.setIntro(request.getIntro());
        article.setData(request.getData());
        article.setArticleCategory(request.getArticleCategory());
        article.setBLock(request.isBLock());
        article.setOneLineReview(request.getOneLineReview());
        article.setWriteTime(request.getWriteTime());
        Long id = articleService.join(article);

        return new CreateArticleResponse(id);
    }


//    private String writer;
//    private String title;
//    private String intro;
//    private String data;
//    private String category;
//    private boolean bLock;
//    private String oneLineReview;
//    private LocalDateTime writeTime;

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class ArticleListDto{
        private String title;
        private String writer;
        private LocalDateTime writeTime;
    }

    @Data
    @AllArgsConstructor
    static class ArticleDto{
        private String writer;
        private String title;
        private String intro;
        private String data;
        private String category;
        private boolean bLock;
        private String oneLineReview;
        private LocalDateTime writeTime;
    }

//    //update
//    @PutMapping("/api/v2/members/{id}")
//    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
//                                               @RequestBody @Valid UpdateMemberRequest request){
//
//
//        memberService.update(id, request.getName());
//        Member findMember = memberService.findOne(id);
//        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
//    }
//}

    @Data
    static class CreateArticleResponse{
        private Long id;

        public CreateArticleResponse(Long id)
        {
            this.id = id;
        }
    }

    @Data
    static class CreateArticleRequest{


        private String title;
        private String intro;
        private String data;
        private String articleCategory;
        private boolean bLock;
        private String oneLineReview;
        private LocalDateTime writeTime;

        public CreateArticleRequest()
        {

        }

        public CreateArticleRequest(String title, String intro, String data, String articleCategory, boolean bLock, String oneLineReview, LocalDateTime writeTime) {

            this.title = title;
            this.intro = intro;
            this.data = data;
            this.articleCategory = articleCategory;
            this.bLock = bLock;
            this.oneLineReview = oneLineReview;
            this.writeTime = writeTime;
        }
    }

}
