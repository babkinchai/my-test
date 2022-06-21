package org.example.mytest.repository;

import org.example.mytest.entity.Question;
import org.example.mytest.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findByThemeIdAndQuestionNumber(Theme themeId, Integer questionNumber);
    Boolean existsByThemeIdAndQuestionNumber(Theme themeId, Integer questionNumber);

    void deleteById(Long id);

    List<Question> findByTheme_id(Long themeId);
}
