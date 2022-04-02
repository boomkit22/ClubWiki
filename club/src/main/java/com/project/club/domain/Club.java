package com.project.club.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Club {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ClubMemberInfo> clubMemberInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubInterest> interestList = new ArrayList<>();

    //One to Many
    //todo
    //private List<Board> boardList = new ArrayList<>();

    public Club()
    {

    }

    public void setInterestList(List<ClubInterest> interestList) {
        this.interestList = interestList;
        for(ClubInterest interest : interestList){
            interest.setClub(this);
        }
    }

    public Club(String name, List<ClubMemberInfo> clubMemberInfoList, List<ClubInterest> interestList) {
        this.name = name;
        this.clubMemberInfoList = clubMemberInfoList;
        this.interestList = interestList;
    }

    public Club(String name, List<ClubInterest> interestList) {
        this.name = name;
        this.setInterestList(interestList);
    }
}
