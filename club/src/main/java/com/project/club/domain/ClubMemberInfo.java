package com.project.club.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ClubMemberInfo {

    @Id
    @GeneratedValue
    @Column(name= "clubmemberinfo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;


    private String role;

    public ClubMemberInfo() {
    }

    public ClubMemberInfo(Long id, Member member, Club club) {
        this.id = id;
        this.member = member;
        this.club = club;
    }
}
