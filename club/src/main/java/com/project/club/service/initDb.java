package com.project.club.service;

import com.project.club.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
//        return;
        initService.memberInit();
        initService.clubInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        public void memberInit(){

            List<StudentInterest> interestList = new ArrayList<>();
            StudentInterest interest1 = new StudentInterest();
            interest1.setInterest("training");
            StudentInterest interest2 = new StudentInterest();
            interest2.setInterest("music");
//
            interestList.add(interest1);
            interestList.add(interest2);
//

            String password = encoder.encode("qkd077023!");

            Member member1 = new Member(201820783L, "김정훈" , Department.Software.toString(), "asdfjo@ajou.ac.kr", password, interestList,true, Role.Member.toString() ,"USER");
            em.persist(member1);
//
//            Member member2 = new Member(201720716L, "정다진", Department.Media.toString(),"dajin@ajou.ac.kr",interestList,true,Role.Member.toString());
//            em.persist(member2);

        }

        public void clubInit(){
            {
                List<ClubInterest> interestList = new ArrayList<>();
                ClubInterest interest1 = new ClubInterest();
                interest1.setInterest("운동");
                ClubInterest interest2 = new ClubInterest();
                interest2.setInterest("복싱");

                interestList.add(interest1);
                interestList.add(interest2);

                Club club = new Club("호완", interestList);
                em.persist(club);
            }

            {
                List<ClubInterest> interestList = new ArrayList<>();
                ClubInterest interest1 = new ClubInterest();
                interest1.setInterest("음악");
                ClubInterest interest2 = new ClubInterest();
                interest2.setInterest("댄스");

                interestList.add(interest1);
                interestList.add(interest2);

                Club club = new Club("비트", interestList);
                em.persist(club);
            }
        }

        
    }
}
