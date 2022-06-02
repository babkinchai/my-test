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

    @Column(name="question")
    @ManyToOne(fetch = FetchType.EAGER)
    private Question questionId;

    @OneToOne(mappedBy = "correct_answer")
    private Question correctAnswer;

    private String answer;
}
