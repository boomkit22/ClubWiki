package com.project.club.controller;

import com.project.club.controller.form.MemberForm;
import com.project.club.domain.StudentInterest;
import com.project.club.domain.Member;
import com.project.club.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("interestList")
    public Map<String, StudentInterest> interests() {
        Map<String, StudentInterest> interestList = new LinkedHashMap<>();

        interestList.put("운동" , new StudentInterest("training"));
        interestList.put("음악" , new StudentInterest("music"));

        return interestList;
    }

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        log.info("wtf1");

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){

        log.info("wtf2");
        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        Member member = new Member();
        member.setId((form.getId()));
        member.setName((form.getName()));
        member.setDepartment((form.getDepartment()));
        member.setEmail((form.getEmail()));
        member.setInterestList((form.getInterestList()));
        member.setBReceiveMail((form.isBReceiveMail()));
        member.setRole((form.getRole()));
        member.setPassword((encoder.encode(form.getPassword())));
        member.setAuth((form.getAuth()));
        member.setSelfIntroduction(form.getSelfIntroduction());

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
