package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.listener.TelegramBotUpdatesListener;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {
    private final TelegramBotUpdatesListener listener;
    private final RecommendationsService recommendationsService;

    public TelegramBotService(TelegramBotUpdatesListener listener, RecommendationsService recommendationsService) {
        this.listener = listener;
        this.recommendationsService = recommendationsService;
    }


    public void setTask(Long chatId, String text) {
        if(!text.isEmpty()){
            if (text.charAt(0)=='/') {
                if(text.equals("/recommend")){
                    listener.sendingMessage(chatId,"Пока так");
                }
                if(text.equals("/r")){
                    System.out.println(recommendationsService.getUser("sheron.berge"));
                    listener.sendingMessage(chatId,"Пока так");
                }
            }
        }
    }

}
