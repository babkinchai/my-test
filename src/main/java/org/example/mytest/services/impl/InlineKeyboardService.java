package org.example.mytest.services.impl;


import org.example.mytest.entity.Answer;
import org.example.mytest.entity.Question;
import org.example.mytest.entity.Theme;
import org.example.mytest.repository.ThemeRepository;
import org.example.mytest.services.InlineKeyboardServiceInterface;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InlineKeyboardService implements InlineKeyboardServiceInterface {

    private final ThemeRepository themeRepository;

    public InlineKeyboardService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @Override
    public InlineKeyboardMarkup sendQuestionsTopic() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<InlineKeyboardButton>();
        List<Theme> themeList = themeRepository.findAll();
        for (Theme theme : themeList) {
            InlineKeyboardButton themeButton = new InlineKeyboardButton(theme.getName_theme());
            themeButton.setCallbackData("/theme/"+ theme.getId());
            buttons.add(themeButton);
        }
        keyboard.add(buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup sendQuestions(Question question) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<InlineKeyboardButton>();
        for (Answer answer:question.getAnswerList()  ) {
            InlineKeyboardButton questionButton1 = new InlineKeyboardButton(answer.getAnswer());
            questionButton1.setCallbackData("/answer/"+ answer.getId());
            buttons.add(questionButton1);
        }
        Random rnd = new Random();
        int number = rnd.nextInt(4)%4 ;
        InlineKeyboardButton keyboardButton=buttons.get(3);
        buttons.set(3,buttons.get(number));
        buttons.set(number,keyboardButton);

        keyboard.add(buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}
