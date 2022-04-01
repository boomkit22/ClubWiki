package com.project.club;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Interest> interestList = new ArrayList<>();

    private boolean bReceiveMail;
    private String role;


    public Member() {
    }

    public Member(Long id, String name, String department, String email, List<Interest> interestList, boolean bReceiveMail, String role) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
        this.interestList = interestList;
        this.bReceiveMail = bReceiveMail;
        this.role = role;
    }

    public void setInterestList(List<Interest> interestList) {
        this.interestList = interestList;
        for(Interest interest : interestList){
            interest.setMember(this);
        }
    }
}

