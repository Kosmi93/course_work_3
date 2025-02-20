package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.UserDTO;
import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.listener.TelegramBotUpdatesListener;
import com.TeamToWin.course_work.model.Recommendation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelegramBotService {
    private final TelegramBotUpdatesListener listener;
    private final RecommendationsService recommendationsService;

    public TelegramBotService(TelegramBotUpdatesListener listener, RecommendationsService recommendationsService) {
        this.listener = listener;
        this.recommendationsService = recommendationsService;
    }


    public void setTask(Long chatId, String text) {
        Optional<UserRecommendation> userRecommendation;
        if(!text.isEmpty()){
            if (text.charAt(0)=='/') {
                String[] comands = text.split(" ");
                if (comands[0].equals("/start")) {
                    listener.sendingMessage(chatId,"Здравствуйте! Для получения информации по банковским продуктам введите \"/recommend <username>\"");
                }else
                if(comands[0].equals("/recommend")){
                    List<UserDTO> users = recommendationsService.getUser(comands[1]);
                    if(users.size()>1 || users.isEmpty()){
                        listener.sendingMessage(chatId,"Пользователь не найден");
                    } else {
                        listener.sendingMessage(chatId,"Здравствуйте " + users.get(0).getFirstName()+ " "+ users.get(0).getLastName());
                        userRecommendation = recommendationsService.getRecommendationsRule(users.get(0).getId());
                        listener.sendingMessage(chatId,"Новые продукты для вас: ");
                        for (Recommendation recommendations : userRecommendation.get().getRecommendations()) {
                            listener.sendingMessage(chatId, "Продукт: \" " + recommendations.getProductName() + "\" " + '\n' + recommendations.getProductText());
                        }
                    }
                }else {
                    listener.sendingMessage(chatId,"Неизвестная команда");
                }
            }
        }
    }

}
