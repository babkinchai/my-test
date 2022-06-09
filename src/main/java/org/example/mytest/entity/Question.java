package org.example.mytest.entity;

import lombok.Data;
import org.example.mytest.dto.QuestionDto;

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

    private Theme theme;

    private String question;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Answer> answerList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "correct_answer", referencedColumnName = "id")
    private Answer correct_answer;
    private Integer question_number;

}
