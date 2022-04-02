package com.project.club.service;

import com.project.club.domain.Club;
import com.project.club.domain.ClubMemberInfo;
import com.project.club.domain.Member;
import com.project.club.respository.ClubMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;

    @Autowired
    public ClubMemberService(ClubMemberRepository clubMemberRepository)
    {
        this.clubMemberRepository = clubMemberRepository;
    }

    @Transactional
    public Long join(ClubMemberInfo clubMemberInfo){
        clubMemberRepository.save(clubMemberInfo);
        return clubMemberInfo.getId();
    }

    public List<ClubMemberInfo> findByMember(Member member)
    {
        return clubMemberRepository.findByMember(member);
    }


    @Transactional
    public Long deleteById(Long id){
        clubMemberRepository.deleteById(id);
        return id;
    }

//    @Transactional
//    public void deleteByClubMember(Club club, Member member){
//        clubMemberRepository.deleteByClubMember(club, member);
//    }
}
