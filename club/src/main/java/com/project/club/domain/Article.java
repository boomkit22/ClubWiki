//package com.project.club;
//
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//public class Article {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//
//    @ManyToOne
//    @JoinColumn(name = "writer_student_id")
//    private Member writer;
//
//    private String data;
//
//    public Article(){
//
//
//    }
//
//    public Article(Long id, Member writer, String data) {
//        this.id = id;
//        this.writer = writer;
//        this.data = data;
//    }
//
//}
