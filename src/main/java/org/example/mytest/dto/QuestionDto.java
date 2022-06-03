package org.example.mytest.dto;

import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String theme;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Integer correct_answer;
    private Integer question_number;
}
