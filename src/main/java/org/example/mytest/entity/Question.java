package org.example.mytest.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Theme themeId;
    private String question;
    @Column(name = "questions")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Answer> answerList;
    private Integer questionNumber;
}
