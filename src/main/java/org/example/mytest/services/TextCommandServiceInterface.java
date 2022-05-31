package org.example.mytest.services;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface TextCommandServiceInterface {

    void getTextMessage(Message update);

    String startBotMessage(Message update);
}
