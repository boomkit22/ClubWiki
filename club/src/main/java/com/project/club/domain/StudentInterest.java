package com.project.club.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@IdClass(ItemKey.class)
public class StudentInterest {

    
    //todo 
    //id와 interest를 primary key로 하고
    //id는 member의 id로
//    @Id
//    @GeneratedValue
//    private Long id;


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id") // foreign key 이름
    private Member member;

    @Id
    private String interest;


    public void setMember(Member member)
    {
        this.member=member;
    }

    public StudentInterest(String interest) {
        this.interest = interest;
    }

    public StudentInterest() {
    }
}