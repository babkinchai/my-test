package org.example.mytest.services.impl;

import org.example.mytest.entity.Answer;
import org.example.mytest.entity.Question;
import org.example.mytest.entity.Student;
import org.example.mytest.entity.TestResult;
import org.example.mytest.repository.AnswerRepository;
import org.example.mytest.repository.QuestionRepository;
import org.example.mytest.repository.StudentRepository;
import org.example.mytest.repository.TestResultRepository;
import org.example.mytest.services.CallbackServiceInterface;
import org.example.mytest.services.InlineKeyboardServiceInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;

@Service
public class CallbackService extends DefaultAbsSender implements CallbackServiceInterface {

    @Value("${token}")
    private String token;

    private final QuestionRepository questionRepository;
    private final InlineKeyboardServiceInterface inlineKeyboardServiceInterface;
    private final AnswerRepository answerRepository;
    private final StudentRepository studentRepository;
    private final TestResultRepository testResultRepository;

    public CallbackService(QuestionRepository questionRepository, InlineKeyboardServiceInterface inlineKeyboardServiceInterface, AnswerRepository answerRepository, StudentRepository studentRepository, TestResultRepository testResultRepository) {
        super(new DefaultBotOptions());
        ;
        this.questionRepository = questionRepository;
        this.inlineKeyboardServiceInterface = inlineKeyboardServiceInterface;
        this.answerRepository = answerRepository;
        this.studentRepository = studentRepository;
        this.testResultRepository = testResultRepository;
    }

    @Override
    public void getCallback(CallbackQuery callbackQuery) throws TelegramApiException {
        if (callbackQuery.getData().matches("/answer/(.*)")) {
            String answerId = callbackQuery.getData().substring(8);
            Optional<Answer> optionalAnswer = answerRepository.findById(Long.valueOf(answerId));
            if (optionalAnswer.isEmpty()) {
                return;
            }
            Student student = studentRepository.findByTelegramId(callbackQuery.getFrom().getId());
            if (testResultRepository.existsByAnswerAndStudent(optionalAnswer.get(), student)) {
                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
                answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
                answerCallbackQuery.setText("Вы уже дали ответ на этот вопрос");
                answerCallbackQuery.setShowAlert(false);
                execute(answerCallbackQuery);
                return;
            } else {
                TestResult testResult = new TestResult();
                testResult.setAnswer(optionalAnswer.get());
                testResult.setStudent(student);
                testResult.setTheme(optionalAnswer.get().getQuestionId().getThemeId());
                testResultRepository.save(testResult);
                if (questionRepository.existsByThemIdAndQuestion_number(
                        optionalAnswer.get()
                                .getQuestionId()
                                .getThemeId().getId()
                        , optionalAnswer.get().getQuestionId()
                                .getQuestion_number() + 1)) {

                    Question question = questionRepository.findByThemeIdAndQuestion_number(
                            optionalAnswer.get()
                                    .getQuestionId()
                                    .getThemeId().getId()
                            , optionalAnswer.get().getQuestionId()
                                    .getQuestion_number() + 1);
                    SendMessage message = new SendMessage();
                    message.setText("Вопрос №1\n" + question.getQuestion());
                    message.setReplyMarkup(inlineKeyboardServiceInterface.sendQuestions(question));
                    message.setChatId(String.valueOf(callbackQuery.getFrom().getId()));
                    execute(message);
                } else {
                    SendMessage message = new SendMessage();
                    List<TestResult> testResultRepositoryByThemeAndStudent = testResultRepository.findByThemeAndStudent(
                            optionalAnswer.get()
                                    .getQuestionId()
                                    .getThemeId(),
                            student.getId());
                    int size = (int) testResultRepositoryByThemeAndStudent.stream()
                            .filter(testResult1 -> testResult1.getAnswer().getCorrectAnswer())
                            .count();
                    message.setText("Ваш результат " + size + " из " + testResultRepositoryByThemeAndStudent.size());
                    message.setChatId(String.valueOf(callbackQuery.getFrom().getId()));
                    execute(message);
                }
            }
        } else if (callbackQuery.getData().matches("/theme/(.*)")) {
            String themeId = callbackQuery.getData().substring(7);
            Question question = questionRepository.findByThemeIdAndQuestion_number(Long.valueOf(themeId), 1);
            SendMessage message = new SendMessage();
            message.setText("Вопрос №1\n" + question.getQuestion());
            message.setReplyMarkup(inlineKeyboardServiceInterface.sendQuestions(question));
            message.setChatId(String.valueOf(callbackQuery.getFrom().getId()));
            execute(message);
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }


}
