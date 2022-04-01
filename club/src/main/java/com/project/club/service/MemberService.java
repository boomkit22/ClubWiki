package com.project.club.service;

import com.project.club.Member;
import com.project.club.respository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository)
    {
        this.memberRepository = memberRepository;
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

//    public Member findOne(Long id) { return memberRepository.findOne(id);}
//
//    public List<Member> findByName(String name) { return memberRepository.findByName(name);}



    @Transactional
    public Long join(Member member)
    {
//        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

//    private void validateDuplicateMember(Member member) {
//        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
//        if(!findMembers.isEmpty()){
//            throw new IllegalStateException("이미 존재하는 회원");
//        }


//    }


    @Override
    public Member loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((email)));
    }

}
