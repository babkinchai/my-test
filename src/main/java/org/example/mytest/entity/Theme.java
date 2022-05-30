package org.example.mytest.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_theme")
@Data
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name_theme;
}
