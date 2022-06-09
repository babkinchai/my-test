package org.example.mytest.DAO;

import com.google.common.collect.Lists;
import org.example.mytest.dto.AnswerDto;
import org.example.mytest.dto.QuestionDto;
import org.example.mytest.entity.Answer;
import org.example.mytest.entity.Question;
import org.example.mytest.entity.Theme;
import org.example.mytest.repository.QuestionRepository;
import org.example.mytest.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<AnswerDto> answerDtos = dto.getAnswers();
        List<Answer> list = answerDtos.stream().map(answerDto -> {
            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setAnswer(answerDto.getAnswer());
            if (answerDto.getIsCorrect() != null) {
                answer.setCorrect_answer(question);
            }
            return answer;
        }).collect(Collectors.toList());
        list.stream().forEach(answer -> answer.setQuestion(question));
        ;

        question.setQuestion(dto.getQuestion());
        question.setAnswerList(list);


        String theme_name = dto.getTheme();
        Theme theme = themeRepository.findThemeByName(theme_name);


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
