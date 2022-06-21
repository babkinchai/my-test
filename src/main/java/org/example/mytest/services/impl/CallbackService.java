package org.example.mytest.services.impl;

import org.example.mytest.entity.Answer;
import org.example.mytest.entity.Question;
import org.example.mytest.entity.Student;
import org.example.mytest.entity.TestResult;
import org.example.mytest.repository.*;
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

    @Value("${bot.token}")
    private String token;

    private final QuestionRepository questionRepository;
    private final InlineKeyboardServiceInterface inlineKeyboardServiceInterface;
    private final AnswerRepository answerRepository;
    private final StudentRepository studentRepository;
    private final TestResultRepository testResultRepository;
    private final ThemeRepository themeRepository;

    public CallbackService(QuestionRepository questionRepository, InlineKeyboardServiceInterface inlineKeyboardServiceInterface, AnswerRepository answerRepository, StudentRepository studentRepository, TestResultRepository testResultRepository, ThemeRepository themeRepository) {
        super(new DefaultBotOptions());
        ;
        this.questionRepository = questionRepository;
        this.inlineKeyboardServiceInterface = inlineKeyboardServiceInterface;
        this.answerRepository = answerRepository;
        this.studentRepository = studentRepository;
        this.testResultRepository = testResultRepository;
        this.themeRepository = themeRepository;
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
            } else {
                TestResult testResult = new TestResult();
                testResult.setAnswer(optionalAnswer.get());
                testResult.setStudent(student);
                testResult.setTheme(optionalAnswer.get().getQuestionId().getThemeId());
                testResultRepository.save(testResult);
                if (questionRepository.existsByThemeIdAndQuestionNumber(
                        optionalAnswer.get()
                                .getQuestionId()
                                .getThemeId()
                        , optionalAnswer.get().getQuestionId()
                                .getQuestionNumber() + 1)) {

                    Question question = questionRepository.findByThemeIdAndQuestionNumber(
                            optionalAnswer.get()
                                    .getQuestionId()
                                    .getThemeId()
                            , optionalAnswer.get().getQuestionId()
                                    .getQuestionNumber() + 1);
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
            if(!themeRepository.existsById(Long.valueOf(themeId))){
                return;
            }
            Question question = questionRepository.findByThemeIdAndQuestionNumber(themeRepository.findById(Long.valueOf(themeId)).get(), 1);
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
