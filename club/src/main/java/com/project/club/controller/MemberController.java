package com.project.club.controller;

import com.project.club.Interest;
import com.project.club.Member;
import com.project.club.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("interestList")
    public Map<String, Interest> interests() {
        Map<String, Interest> interestList = new LinkedHashMap<>();

        interestList.put("운동" , new Interest("training"));
        interestList.put("음악" , new Interest("music"));

        return interestList;
    }

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Member member = new Member();
        member.setId((form.getId()));
        member.setName((form.getName()));
        member.setDepartment((form.getDepartment()));
        member.setEmail((form.getEmail()));
        member.setInterestList((form.getInterestList()));
        member.setBReceiveMail((form.isBReceiveMail()));
        member.setRole((form.getRole()));

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
