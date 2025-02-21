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


    public void getTask(Long chatId, String text) {
        if (!text.isEmpty()) {
            if (text.charAt(0) == '/') {
                String[] comands = text.split(" ");
                if (comands[0].equals("/start")) {
                    listener.sendingMessage(chatId, "Здравствуйте! Для получения информации по банковским продуктам введите \"/recommend <username>\"");
                }else
                if (comands[0].equals("/recommend") && comands.length > 1) {
                    List<UserDTO> users = recommendationsService.getUser(comands[1]);
                    if (users.size() > 1 || users.isEmpty()) {
                        listener.sendingMessage(chatId, "Пользователь не найден");
                    } else {
                        listener.sendingMessage(chatId, getTextRecommendations(users.get(0)));
                    }
                } else {
                    listener.sendingMessage(chatId, "Неизвестная команда");
                }
            }
        }
    }

    private String getTextRecommendations(UserDTO user) {
        Optional<UserRecommendation> userRecommendation = recommendationsService
                .getRecommendationsRule(user.getId());
        StringBuilder result = new StringBuilder();
        result.append("Здравствуйте ");
        result.append(user.getFirstName() + " " + user.getLastName() + '\n');
        result.append("Новые продукты для вас:");
        result.append('\n');
        result.append('\n');
        if (userRecommendation.isPresent()) {
            for (Recommendation recommendation : userRecommendation.get().getRecommendations()) {
                result.append(recommendation.getProductName());
                result.append('\n');
                result.append(recommendation.getProductText());
                result.append('\n');
                result.append('\n');
            }
        } else {
            result.append("Для вас новых продуктов не найдено");
        }
        return result.toString();
    }

}
