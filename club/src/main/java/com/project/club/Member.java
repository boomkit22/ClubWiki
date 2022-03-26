package com.project.club;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id
    @Column(name = "student_id")
    private Long id;

    private String name;

    private String role;

    public Member() {
    }

    public Member(Long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
