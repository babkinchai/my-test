package org.example.mytest.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Theme theme_id;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correct_answer;
    private Integer question_number;

}
