package com.TeamToWin.course_work.listener;

import com.TeamToWin.course_work.service.TelegramBotService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    @Lazy
    @Autowired
    private TelegramBotService controller;



    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            String msg = update.message().text();
            if (update.message() != null) {
                controller.setTask(update.message().chat().id(), update.message().text());
            }
            System.out.println(update.message());
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void sendingMessage(Long chatId, String text) {
        SendMessage request = new SendMessage(chatId, text);
        telegramBot.execute(request);
    }

}
