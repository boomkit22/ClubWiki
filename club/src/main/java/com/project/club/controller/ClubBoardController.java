package com.project.club.controller;

import com.project.club.domain.Club;
import com.project.club.domain.ClubBoard;
import com.project.club.domain.Member;
import com.project.club.service.ClubBoardService;
import com.project.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ClubBoardController {

    private final ClubService clubService;
    private final ClubBoardService clubBoardService;


    //user가 소속되어있는 클럽의 클럽보드 list를 보여줌
    @GetMapping("/clubBoards")
    public String intoClub(@AuthenticationPrincipal Member member, Model model, @RequestParam(name="clubId") Long id)
    {
        Club club = clubService.findOne(id);

        List<ClubBoard> clubBoards = clubBoardService.findByClub(club);

        model.addAttribute("clubBoards", clubBoards);

        model.addAttribute("clubId", club.getId());

        return "clubs/clubBoardList";
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
        Club club = clubService.findOne(id);
        clubBoard.setClub(club);

        clubBoardService.join(clubBoard);


        String url = "redirect:/clubBoards?clubId="+ id.toString();
        return url;
    }




}
