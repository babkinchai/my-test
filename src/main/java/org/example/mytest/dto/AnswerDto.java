package org.example.mytest.dto;

import lombok.Data;

@Data
public class AnswerDto {
    private Long id;
    private String answer;
    private Boolean isCorrect;
}
