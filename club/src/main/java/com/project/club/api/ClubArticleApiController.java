package com.project.club.api;

//todo
//clubBoard로 전체 article
//article 생성
//article 읽기
//article 수정

import com.project.club.api.dto.*;
import com.project.club.domain.Article;
import com.project.club.domain.ClubBoard;
import com.project.club.domain.ImageFile;
import com.project.club.domain.Member;
import com.project.club.service.ArticleService;
import com.project.club.service.ClubBoardService;
import com.project.club.service.ImageFileService;
import com.project.club.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    private final ImageFileService imageFileService;

    //위키 게시판 한개 속 전체 게시글 내용 불러오기
    @GetMapping("/api/clubs/allArticles/{clubBoardId}")
    public WikiPageDto allArticleList(@PathVariable("clubBoardId") Long clubBoardId)
    {
        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);
        List<Article> articleList = articleService.findByClubBoard(clubBoard);

        List<ArticleListDto> collect = articleList.stream().map(m-> new ArticleListDto(m.getId(), m.getData(), Long.valueOf(m.getImageFileList().size())
                ,(m.getImageFileList().stream().map(i-> new ImageFileDto(i.getFilePath(),i.getFileName(), i.getFileType(), i.getFileSize())).collect(Collectors.toList()))
                , m.getCreatedDate()
             )).collect(Collectors.toList());

        String wikiName = clubBoard.getName();
        String wikiIntro = clubBoard.getIntro();
        String cpAnnouncement = clubBoard.getOneLineReview();
        boolean isLock = clubBoard.isBLock();
        Long articleCount = Long.valueOf(articleList.size());


        return WikiPageDto.builder()
                .wikiName(wikiName)
                .wikiIntro(wikiIntro)
                .cpAnnouncement(cpAnnouncement)
                .isLock(isLock)
                .articleCount(articleCount)
                .articleList(collect)
                .build();

    }

        @PostMapping("/api/clubs/articles/{clubBoardId}")
    public CreateArticleResponseDto saveArticle(@PathVariable("clubBoardId") Long clubBoardId,
                                                @RequestParam(name="memberId") Long memberId,
                                                @RequestBody @Valid CreateArticleRequestDto request)
    {
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
        List memberArticleList = member.getArticleList();
        memberArticleList.add(article);
        member.setArticle(memberArticleList);
        article.setData(request.getData());


        if(request.getImageFileList().isEmpty())
        {

        }
        else
        {
            List<ImageFileDto> imageFileDtoList = request.getImageFileList();
            List<ImageFile> imageFileList = imageFileDtoList.stream().map(m -> ImageFile.builder()
                    .fileName(m.getFileName())
                    .filePath(m.getFilePath())
                    .fileType(m.getFileType())
                    .fileSize(m.getFileSize())
                    .build())
                    .collect(Collectors.toList());

             article.setImageFile(imageFileList);
        }

        Long id = articleService.save(article);

        return  CreateArticleResponseDto.builder()
                .id(id)
                .memberId(memberId)
                .memberName(member.getName())
                .data(article.getData())
                .createDate(article.getCreatedDate())
                .modifiedDate(article.getModifiedDate())
                .imageFileList(article.getImageFileList()
                        .stream().map(ImageFileDto::new).collect(Collectors.toList()))
                .build();

    }

    @GetMapping("/api/clubs/articles/{articleId}")
    public ArticleDto article(@PathVariable("articleId") Long articleId)
    {
        Article article = articleService.findById(articleId);

        ArticleDto articleDto = new ArticleDto(article.getMember().getName()
        ,article.getData(),article.getImageFileList().stream().map(ImageFileDto::new
                ).collect(Collectors.toList())
                ,article.getCreatedDate()
                ,article.getModifiedDate());

        return articleDto;
    }
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