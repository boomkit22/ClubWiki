package com.project.club.service;

import com.project.club.Department;
import com.project.club.Interest;
import com.project.club.Member;
import com.project.club.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor

public class initDb {
    private final InitService initService;
    @PostConstruct
    public void Init(){
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInit(){

//            List<Interest> interestList = new ArrayList<>();
//            Interest interest1 = new Interest();
//            interest1.setInterest("training");
//            Interest interest2 = new Interest();
//            interest2.setInterest("music");
//
//            interestList.add(interest1);
//            interestList.add(interest2);
//
//            Member member1 = new Member(201820783L, "김정훈" , Department.Software.toString(), "asdfjo@ajou.ac.kr", interestList,true, Role.Member.toString());
//            em.persist(member1);
//
//            Member member2 = new Member(201720716L, "정다진", Department.Media.toString(),"dajin@ajou.ac.kr",interestList,true,Role.Member.toString());
//            em.persist(member2);

        }

        
    }
}
