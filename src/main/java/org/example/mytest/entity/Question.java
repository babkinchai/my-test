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
    private Theme theme_id;
    private String question;
    @Column(name = "questions")
    @OneToMany(fetch = FetchType.EAGER)
    private List<Answer> answerList;
    @OneToOne(mappedBy = "id")
    private Answer correct_answer;
    private Integer question_number;

}
