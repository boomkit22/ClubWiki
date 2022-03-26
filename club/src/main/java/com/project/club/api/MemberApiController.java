package com.project.club.api;

import com.project.club.Member;
import com.project.club.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1(){
        return memberService.findMembers();
    }


    @GetMapping("/api/v1/members/id/{id}")
    public Member membersOneV1(@PathVariable("id") Long id){

        Member result = memberService.findOne(id);
        return result;
    }

    @GetMapping("api/v1/members/name/{name}")
    public List<Member> membersByName(@PathVariable("name") String name){
        List<Member> result = memberService.findByName(name);
        return result;
    }

}
