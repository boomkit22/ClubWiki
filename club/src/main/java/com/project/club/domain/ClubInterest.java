package com.project.club.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@IdClass(ClubInterestKey.class)
public class ClubInterest {


    //todo
    //id와 interest를 primary key로 하고
    //id는 member의 id로
//    @Id
//    @GeneratedValue
//    private Long id;


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="club_id") // foreign key 이름
    private Club club;

    @Id
    private String interest;


    public void setClub(Club club)
    {
        this.club=club;
    }

    public ClubInterest(String interest) {
        this.interest = interest;
    }

    public ClubInterest() {
    }
}