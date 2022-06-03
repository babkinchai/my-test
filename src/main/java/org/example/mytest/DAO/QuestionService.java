package org.example.mytest.DAO;

import org.example.mytest.dto.QuestionDto;
import org.example.mytest.entity.Question;
import org.example.mytest.entity.Theme;
import org.example.mytest.repository.QuestionRepository;
import org.example.mytest.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ThemeRepository themeRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Boolean save(QuestionDto dto) {
        Question question = new Question();
        String theme_name = dto.getTheme();
        Theme theme = themeRepository.findThemeByName(theme_name);
        question.setAttributesFromDto(dto);
        question.setTheme(theme);
        questionRepository.save(question);
        return true;
    }

    public Boolean deleteQuestion(Long id) {
        if (questionRepository.findById(id).isPresent()) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
