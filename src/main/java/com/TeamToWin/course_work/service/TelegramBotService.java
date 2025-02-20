package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.UserDTO;
import com.TeamToWin.course_work.listener.TelegramBotUpdatesListener;
import org.springframework.stereotype.Service;

import java.util.List;

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
                String[] comands = text.split(" ");
                if(comands[0].equals("/recommend")){
                    List<UserDTO> users = recommendationsService.getUser(comands[1]);
                    if(users.size()>1 || users.isEmpty()){
                        listener.sendingMessage(chatId,"Пользователь не найден");
                    } else {
                        listener.sendingMessage(chatId,users.get(0).getFirstName());
                    }
                }else {
                    listener.sendingMessage(chatId,"Неизвестная команда");
                }
            }
        }
    }

}
