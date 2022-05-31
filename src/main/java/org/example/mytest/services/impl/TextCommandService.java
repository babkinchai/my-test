package org.example.mytest.services.impl;


import org.example.mytest.entity.Student;
import org.example.mytest.repository.StudentRepository;
import org.example.mytest.services.InlineKeyboardServiceInterface;
import org.example.mytest.services.TextCommandServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextCommandService extends DefaultAbsSender implements TextCommandServiceInterface {

    public static Logger logger = LoggerFactory.getLogger(TextCommandService.class);

    @Value("${bot.token}")
    String token;

    private final StudentRepository studentRepository;
    private InlineKeyboardServiceInterface inlineKeyboardService;


    protected TextCommandService(StudentRepository studentRepository) {
        super(new DefaultBotOptions());

        this.studentRepository = studentRepository;
    }

    @Override
    public void getTextMessage(Message message) {
        switch (message.getText()) {
            case ("/start"): {
                startBotMessage(message);
                sendCustomKeyboard(message.getChatId().toString());
                break;
            }
            case ("Выбрать тест"): {
                InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.sendQuestionsTopic();
                getSendInlineKeyboard(inlineKeyboardMarkup,message.getChatId().toString());
                break;
            }
            default:
                try {
                    execute(new SendMessage(message.getChatId().toString(), "Осуждаю"));
                } catch (TelegramApiException e) {
                    logger.error("getTextMessage switch default error. Cant send message for client. " + e.getMessage());
                }

        }
    }

    private void getSendInlineKeyboard(InlineKeyboardMarkup inlineKeyboardMarkup,String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("getSendInlineKeyboard method error. Cant send message for client. " + e.getMessage());
        }
    }

    @Override
    public String startBotMessage(Message message) {
        Student botUsers;
        if (!studentRepository.existsByTelegramId(message.getFrom().getId())){
            botUsers = new Student();
            botUsers.setTelegramUsername(message.getFrom().getUserName());
            botUsers.setId(message.getFrom().getId());
            studentRepository.save(botUsers);
        }else {
            botUsers=studentRepository.findByTelegramId(message.getFrom().getId());
        }
        return "Hello my dear, " + botUsers.getTelegramUsername();
    }

    public void sendCustomKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Добро пожаловать");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Выбрать тест");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("sendCustomKeyboard method error. Cant send message for client. " + e.getMessage());
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
