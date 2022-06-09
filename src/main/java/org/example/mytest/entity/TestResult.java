package org.example.mytest.entity;

import javax.persistence.*;

@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne()
    private Student student;

    @ManyToOne()
    private Answer answer;

}
