package com.project.club.repository;

import com.project.club.domain.ClubInterest;
import com.project.club.domain.Member;
import com.project.club.domain.StudentInterest;
import com.project.club.respository.MemberRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @After
    public void cleanup(){
        memberRepository.deleteAll();
    }

    @Test
    public void 회원저장_불러오기(){
        //given
        Long id = 201820783L;
        String name = "김정훈";
        String department = "소프트웨어학과";
        String email = "asdfjo@ajou.ac.kr";
        String password = "qkd077023!";
        List<StudentInterest> interestList = new ArrayList<>();
        StudentInterest interest1 = new StudentInterest();
        interest1.setInterest("training");
        interestList.add(interest1);
        boolean bAuthenticated = false;
        boolean bReceiveMail = false;
        String role = "MEMBER";
        String auth = "USER";
        memberRepository.save(Member.builder()
                .id(id)
                .name(name)
                .department(department)
                .email(email)
                .interestList(interestList)
                .bAuthenticated(bAuthenticated)
                .bReceiveMail(bReceiveMail)
                .role(role)
                .auth(auth)
                .build()
        );
        //when
        List<Member> memberList = memberRepository.findAll();
        //then
        Member member = memberList.get(0);
        assertThat(member.getId()).isEqualTo(id);
        assertThat(member.getName()).isEqualTo(name);
    }

    @Test
    public void 멤버BaseTimeEntity_등록(){

        //given
        LocalDateTime now = LocalDateTime.of(2022, 5,9,1,34,0);
        Long id = 201820783L;
        String name = "김정훈";
        String department = "소프트웨어학과";
        String email = "asdfjo@ajou.ac.kr";
        String password = "qkd077023!";
        List<StudentInterest> interestList = new ArrayList<>();
        StudentInterest interest1 = new StudentInterest();
        interest1.setInterest("training");
        interestList.add(interest1);
        boolean bAuthenticated = false;
        boolean bReceiveMail = false;
        String role = "MEMBER";
        String auth = "USER";
        memberRepository.save(Member.builder()
                .id(id)
                .name(name)
                .department(department)
                .email(email)
                .interestList(interestList)
                .bAuthenticated(bAuthenticated)
                .bReceiveMail(bReceiveMail)
                .role(role)
                .auth(auth)
                .build()
        );
        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        Member member = memberList.get(0);

        assertThat(member.getCreatedDate()).isAfter(now);
        assertThat(member.getModifiedDate()).isAfter(now);
    }

}
