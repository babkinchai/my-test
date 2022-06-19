package org.example.mytest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne()
    private Student student;

    @ManyToOne()
    private Answer answer;

    @ManyToOne()
    private Theme theme;

}
