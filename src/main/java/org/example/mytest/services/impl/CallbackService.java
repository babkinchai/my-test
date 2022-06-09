package org.example.mytest.services.impl;

import org.example.mytest.entity.Question;
import org.example.mytest.repository.QuestionRepository;
import org.example.mytest.services.CallbackServiceInterface;
import org.example.mytest.services.InlineKeyboardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class CallbackService extends DefaultAbsSender implements CallbackServiceInterface {

    @Value("${token}")
    private String token;

    private final QuestionRepository questionRepository;
    private final InlineKeyboardServiceInterface inlineKeyboardServiceInterface;

    public CallbackService(QuestionRepository questionRepository, InlineKeyboardServiceInterface inlineKeyboardServiceInterface) {
        super(new DefaultBotOptions());
        ;
        this.questionRepository = questionRepository;
        this.inlineKeyboardServiceInterface = inlineKeyboardServiceInterface;
    }

    @Override
    public void getCallback(CallbackQuery callbackQuery) throws TelegramApiException {
        if (callbackQuery.getData().matches("/(.*)/(.*)/(.*)")) {
            List<String> collect = Arrays.stream(callbackQuery.getData().split("/")).collect(Collectors.toList());

        }
        else if (callbackQuery.getData().matches("/theme/(.*)")) {
            String themeId = callbackQuery.getData().substring(7);
            List<Question> questionList = questionRepository.findByTheme_id(Long.valueOf(themeId));
            List<Question> sortQuestions = questionList.stream().sorted(Comparator.comparing(Question::getId))
                    .collect(Collectors.toList());
            SendMessage message = new SendMessage();
            message.setText("Вопрос №1\n" + sortQuestions.get(0).getQuestion());
            message.setReplyMarkup(inlineKeyboardServiceInterface.sendQuestions(sortQuestions.get(0)));
            message.setChatId(String.valueOf(callbackQuery.getFrom().getId()));
            execute(message);
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
