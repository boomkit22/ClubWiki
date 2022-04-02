package com.project.club.respository;

import com.project.club.domain.Club;
import com.project.club.domain.ClubMemberInfo;
import com.project.club.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMemberInfo, Long> {

    public List<ClubMemberInfo> findByMember(Member member);

//    public Long deleteByClubMember(Club club, Member member);
}

