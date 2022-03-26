package com.project.club.service;

import com.project.club.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

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

            Member member1 = new Member(201820783L, "김정훈" , "president");
            em.persist(member1);
            
            Member member2 = new Member(201720716L, "정다진", "manager");
            em.persist(member2);

        }

        
    }
}
