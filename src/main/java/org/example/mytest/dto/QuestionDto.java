package org.example.mytest.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private String theme;
    private List<AnswerDto> answers = new ArrayList<>();
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Integer correct_answer;
    private Integer question_number;
    public void addAnswer(AnswerDto answerDto) {
        answers.add(answerDto);
    }
}
