package org.example.mytest.entity;

import lombok.Data;
import org.example.mytest.dto.QuestionDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_question")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Theme theme;
    @NotNull
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Integer correct_answer;
    private Integer question_number;

    public void setAttributesFromDto(QuestionDto dto) {

        question = dto.getQuestion();
        answer1 = dto.getAnswer1();
        answer2 = dto.getAnswer2();
        answer3 = dto.getAnswer3();
        answer4 = dto.getAnswer4();
        correct_answer = dto.getCorrect_answer();
    }
}
