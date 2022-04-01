package com.project.club;

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
public class Member implements UserDetails {

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Interest> interestList = new ArrayList<>();

    private boolean bReceiveMail;
    private String role;
    public String auth;


    public Member(){

    }

    @Builder
    public Member(Long id, String name, String department, String email, String password, List<Interest> interestList, boolean bReceiveMail, String role, String auth) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
        this.password = password;
        this.interestList = interestList;
        this.bReceiveMail = bReceiveMail;
        this.role = role;
        this.auth = auth;
    }



    public void setInterestList(List<Interest> interestList) {
        this.interestList = interestList;
        for(Interest interest : interestList){
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

