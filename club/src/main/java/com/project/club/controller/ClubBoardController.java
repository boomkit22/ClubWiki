package com.project.club.controller;

import com.project.club.controller.form.ClubBoardForm;
import com.project.club.domain.Article;
import com.project.club.domain.Club;
import com.project.club.domain.ClubBoard;
import com.project.club.domain.Member;
import com.project.club.service.ArticleService;
import com.project.club.service.ClubBoardService;
import com.project.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ClubBoardController {

    private final ClubService clubService;
    private final ClubBoardService clubBoardService;
    private final ArticleService articleService;


    //user가 소속되어있는 클럽의 클럽보드 list를 보여줌
    @GetMapping("/clubBoards")
    public String clubBoardList(@AuthenticationPrincipal Member member, Model model, @RequestParam(name="clubId") Long id)
    {
        Club club = clubService.findOne(id);

        List<ClubBoard> clubBoards = clubBoardService.findByClub(club);

        model.addAttribute("clubBoards", clubBoards);

        model.addAttribute("clubId", club.getId());

        return "clubs/clubBoardList";
    }

    @GetMapping("/clubBoards/{clubBoardId}")
    public String clubArticle(Model model, @PathVariable("clubBoardId") Long clubBoardId)
    {
//        model.addAttribute("clubBoardId",clubBoardId);

        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);
        List<Article> articleList = articleService.findByClubBoard(clubBoard);

        for ( Article article : articleList){

            article.getMember().getName();

        }

        model.addAttribute("articleList", articleList);
        model.addAttribute("clubBoardId", clubBoardId);
        //todo articleService 를 사용한 article List가져오기  clubBoard Id 사용해서

        return "clubs/boards/articleList";
    }



    @GetMapping("/clubBoards/new/{clubId}")
    public String createForm(Model model,@PathVariable("clubId") Long id){
        model.addAttribute("clubBoardForm", new ClubBoardForm());
        model.addAttribute("clubId",id);

        log.info("getMapping");
        return "clubs/createClubBoardForm";
    }

    @PostMapping("/clubBoards/new/{clubId}")
    public String create(@Valid ClubBoardForm form, BindingResult result,@PathVariable("clubId") Long id)
    {

        log.info("postMapping");
        if(result.hasErrors()){
            return "clubs/createClubBoardForm";
        }

        ClubBoard clubBoard = new ClubBoard();
        clubBoard.setName(form.getName());
        clubBoard.setBoardCategory(form.getBoardCategory());
        clubBoard.setBLock(form.isBLock());
        clubBoard.setIntro(form.getIntro());
        clubBoard.setOneLineReview(form.getOneLineReview());

        Club club = clubService.findOne(id);
        clubBoard.setClub(club);

        clubBoardService.join(clubBoard);


        String url = "redirect:/clubBoards?clubId="+ id.toString();

        return url;
    }

    @PostMapping("/clubBoards/lock/{clubBoardId}")
    public String lock(Model model, @PathVariable("clubBoardId") Long clubBoardId)
    {

        ClubBoard clubBoard = clubBoardService.findById(clubBoardId);
        clubBoard.setBLock(!clubBoard.isBLock());
        clubBoardService.join(clubBoard);

        Long clubId = clubBoard.getClub().getId();

        return "redirect:/clubBoards?clubId=" + clubId.toString();
    }


}
