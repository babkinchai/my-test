package org.example.mytest.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Question questionId;

    private Boolean correctAnswer;

    private String answer;
}
