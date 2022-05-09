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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

//잠금,해제 api
@Slf4j
@RestController
@RequiredArgsConstructor
public class ClubArticleApiController {

    private final ArticleService articleService;
    private final ClubBoardService clubBoardService;
    private final MemberService memberService;

    //위키 게시판 한개 속 전체 게시글 내용 불러오기
    //todo 사진
    @GetMapping("/api/clubs/allArticles/{clubBoardId}")
    public Result allArticleList(@PathVariable("clubBoardId") Long clubBoardId)
    {
        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);
        List<Article> articleList = articleService.findByClubBoard(clubBoard);
        List<ArticleListDto> collect = articleList.stream().map(m-> new ArticleListDto(m.getData(), m.getCreatedDate()
             )).collect(Collectors.toList());

        return new Result(collect.size(),collect);
    }

//    @GetMapping("/api/clubs/lockedArticles/{clubBoardId}")
//    public Result lockedArticleList(@PathVariable("clubBoardId") Long clubBoardId
//                                    ,@RequestParam(name="locked") int locked)
//    {
//        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);
//        List<Article> articleList = articleService.findByClubBoard(clubBoard);
//
//        if(locked == 0 )
//        {
//            articleList.removeIf(article -> (article.isBLock() == true));
//        }
//        else if(locked == 1)
//        {
//            articleList.removeIf(article -> (article.isBLock() == false));
//        }
//        List<ArticleListDto> collect = articleList.stream().map(m-> new ArticleListDto(m.getTitle(),m.getMember().getName(),
//                m.getWriteTime())).collect(Collectors.toList());
//
//        return new Result(collect.size(),collect);
//    }




    @GetMapping("/api/clubs/articles/{articleId}")
    public ArticleDto article(@PathVariable("articleId") Long articleId)
    {
        Article article = articleService.findById(articleId);
        ArticleDto articleDto = new ArticleDto(article.getMember().getName()
        ,article.getData());

        return articleDto;
    }

    @PostMapping("/api/clubs/articles/{clubBoardId}")
    public CreateArticleResponse saveArticle(@PathVariable("clubBoardId") Long clubBoardId,
                                             @RequestParam(name="memberId") Long memberId,
                                             @RequestBody @Valid CreateArticleRequest request)
    {
        //todo
        //멤버나 clubBoard가 null인 경우 처리

        Article article = new Article();
        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);

        Member member = memberService.findOne(memberId);
        if(member!= null){
            log.info(member.getName());
        } else if (member == null)
        {
            log.info(memberId.toString());
            log.info("member was null");
        }

        article.setClubBoard(clubBoard);
        article.setMember(member);
        article.setData(request.getData());
        Long id = articleService.save(article);

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
        private String data;
        private LocalDateTime createdDate;
    }

    @Data
    @AllArgsConstructor
    static class ArticleDto{
        private String memberName;
        private String data;
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
        private String data;
        public CreateArticleRequest()
        {

        }

        public CreateArticleRequest(String data) {
            this.data = data;
        }
    }

}
