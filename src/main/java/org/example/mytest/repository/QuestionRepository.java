package org.example.mytest.repository;

import org.example.mytest.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findByThemeIdAndQuestion_number(Long themeId,Integer questionNumber);
    Boolean existsByThemIdAndQuestion_number(Long ThemeId,Integer questionNumber);

}
