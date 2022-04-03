package com.project.club.controller;

import com.project.club.controller.dto.UserClubDto;
import com.project.club.domain.*;
import com.project.club.service.ClubMemberService;
import com.project.club.service.ClubService;
import com.project.club.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final ClubMemberService clubMemberService;

    @ModelAttribute("interestList")
    public Map<String, StudentInterest> interests() {
        Map<String, StudentInterest> interestList = new LinkedHashMap<>();

        interestList.put("운동" , new StudentInterest("training"));
        interestList.put("음악" , new StudentInterest("music"));

        return interestList;
    }

    @GetMapping("/clubs/new")
    public String createForm(Model model)
    {
        model.addAttribute("clubForm",new ClubForm());

        return "clubs/createClubForm";
    }

    @PostMapping("/clubs/new")
    public String create(@Valid ClubForm form, BindingResult result){
        if(result.hasErrors()){
            return "clubs/createClubForm";
        }

        Club club = new Club();
        club.setName(form.getName());
        club.setInterestList(form.getInterestList());

        clubService.join(club);

        return "redirect:/";
    }

    @GetMapping("/allClubs")
    public String list(Model model){
        List<Club> clubs = clubService.findClubs();
        if(clubs.isEmpty()){
            log.info("null");
        }
        model.addAttribute("clubs", clubs);

        return "clubs/allClubList";
    }

    @GetMapping("/userClubs")
    public String userClubList(@AuthenticationPrincipal Member member, Model model){
        Long id = member.getId();
        log.info(id.toString());
        //todo
        //클럽멤버서비스에서 학번으로 가져오기
        List<ClubMemberInfo> clubMemberInfos = clubMemberService.findByMember(member);

        List<UserClubDto> userClubDtos = new ArrayList<>();

        for(ClubMemberInfo info : clubMemberInfos){
            Club club  = clubService.findOne(info.getClub().getId());
            Long clubMemberInfoId = info.getId();
            userClubDtos.add(new UserClubDto(club,clubMemberInfoId));
        }

        if(userClubDtos.isEmpty()){
            log.info("null");
        }

        model.addAttribute("userClubDtos", userClubDtos);

        return "clubs/userClubList";
    }

    @PostMapping("/userClubs/delete")
    public String leaveClub(@AuthenticationPrincipal Member member, @RequestParam(name="clubMemberInfoId") Long id)
    {
        log.info(id.toString());
//        clubService.deleteById(id);
//        Club one = clubService.findOne(id);
//        log.info(one.getName());


        clubMemberService.deleteById(id);


        return "redirect:/";
    }



    @PostMapping("/allClubs/join")
    public String joinClub(@Valid @AuthenticationPrincipal Member member, Model model, @RequestParam(name="clubId") Long id)
    {
        ClubMemberInfo clubMemberInfo = new ClubMemberInfo();
        log.info(id.toString());
        Club club = clubService.findOne(id);
        clubMemberInfo.setClub(club);
        clubMemberInfo.setMember(member);
        clubMemberInfo.setRole("Member");
        clubMemberService.join(clubMemberInfo);
        return "redirect:/";
    }

    @PostMapping("/allClubs/delete")
    public String deleteClub(@RequestParam(name="clubId") Long id){
        log.info(id.toString());
//        clubService.deleteById(id);
        Club one = clubService.findOne(id);
        log.info(one.getName());
        clubService.delete(one);

        return "redirect:/";
    }



}
