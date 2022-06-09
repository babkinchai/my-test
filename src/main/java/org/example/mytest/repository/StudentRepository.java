package org.example.mytest.repository;

import org.example.mytest.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);

    boolean existsByTelegramId(Long telegramId);

    Student findByTelegramId(Long telegramId);
}
