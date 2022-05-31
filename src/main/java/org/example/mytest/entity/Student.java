package org.example.mytest.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_student")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String fio;
    private String email;
    private String password;
    private Date registration_date;
    @Column(name = "telegram_id")
    private Long telegramId;
    @Column(name = "telegram_username")
    private String telegramUsername;
}
