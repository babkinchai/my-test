package org.example.mytest.repository;

import org.example.mytest.entity.Answer;
import org.example.mytest.entity.Student;
import org.example.mytest.entity.TestResult;
import org.example.mytest.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult,Long> {
    boolean existsByAnswerAndStudent(Answer answer, Student student);

    List<TestResult> findByThemeAndStudent(Theme answer_questionId_themeId, Long student_id);
}
