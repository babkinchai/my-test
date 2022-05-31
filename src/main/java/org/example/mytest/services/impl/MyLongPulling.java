package org.example.mytest.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MyLongPulling extends TelegramLongPollingBot {

    public static Logger logger= LoggerFactory.getLogger(MyLongPulling.class);

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onRegister() {
        System.out.println("hello");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if(update.hasMessage()){

            }

        }else if (update.hasCallbackQuery()) {
/*            try {
                callbackServiceInterface.getCallback(update.getCallbackQuery());
            } catch (TelegramApiException e) {
                logger.error("getCallBack method error. Cant send message for client. " + e.getMessage());
            }*/
        }
    }
}


