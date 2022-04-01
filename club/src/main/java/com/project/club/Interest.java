package com.project.club;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Interest{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id") // foreign key 이름
    private Member member;

    private String interest;


    public void setMember(Member member)
    {
        this.member=member;
    }

    public Interest(String interest) {
        this.interest = interest;
    }

    public Interest() {
    }
}