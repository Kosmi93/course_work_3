package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.listener.TelegramBotUpdatesListener;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {
    private final TelegramBotUpdatesListener listener;


    public TelegramBotService(TelegramBotUpdatesListener listener) {
        this.listener = listener;
    }


    public void setTask(Long chatId, String text) {
        if(!text.isEmpty()){
            if (text.charAt(0)=='/') {
                if(text.equals("/recommend")){
                    listener.sendingMessage(chatId,"Пока так");
                }
            }
        }
    }

}
