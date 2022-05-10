package com.project.club.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter @Setter
public class Member extends BaseTimeEntity implements UserDetails {

    //학번
    //이름
    //학과
    //email
    //email 수신여부
    //역할 default 정해야겠는데
    //관심사
    //동아리에 따른 권한 설정
    @Id
    @Column(name = "student_id")
    private Long id;
    private String name;
    private String department;
    private String email;
    private String password;

    private String selfIntroduction;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StudentInterest> interestList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ClubMemberInfo> clubMemberInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Article> articleList = new ArrayList<>();


    private boolean bAuthenticated;
    private boolean bReceiveMail;
    private String role;
    public String auth;


    public Member(){

    }

    @Builder
    public Member(Long id, String name, String department, String email, String password, List<StudentInterest> interestList, boolean bAuthenticated, boolean bReceiveMail, String role, String auth, String selfIntroduction) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
        this.password = password;
        this.setInterestList(interestList);
        this.bAuthenticated = bAuthenticated;
        this.bReceiveMail = bReceiveMail;
        this.role = role;
        this.auth = auth;
        this.selfIntroduction = selfIntroduction;
    }


    public void setArticle(List<Article> articleList) {
        articleList.forEach(article -> article.setMember(this));
        this.articleList = articleList;
    }

    public void setInterestList(List<StudentInterest> interestList) {
        this.interestList = interestList;
        for(StudentInterest interest : interestList){
            interest.setMember(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

