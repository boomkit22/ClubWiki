package com.project.club.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Club extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private String introduction;

    private String location;

    private String phoneNumber;

    private String image_url;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ClubMemberInfo> clubMemberInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubInterest> interestList = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubBoard> clubBoardList = new ArrayList<>();

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

    public Club(String name, List<ClubMemberInfo> clubMemberInfoList, List<ClubInterest> interestList, List<ClubBoard> clubBoardList) {
        this.name = name;
        this.clubMemberInfoList = clubMemberInfoList;
        this.interestList = interestList;
        this.clubBoardList = clubBoardList;
    }

    public Club(String name, List<ClubInterest> interestList) {
        this.name = name;
        this.setInterestList(interestList);
    }

    @Builder
    public Club(String name, String introduction, String location, String phoneNumber, String image_url) {
        this.name = name;
        this.introduction = introduction;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.image_url = image_url;
    }
}
