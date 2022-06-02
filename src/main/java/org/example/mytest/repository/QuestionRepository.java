package org.example.mytest.repository;

import org.example.mytest.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByTheme_id(Long themeId);
}
