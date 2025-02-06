package com.TeamToWin.course_work.rule;

import com.TeamToWin.course_work.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

/* Интерфейс с одним методом, который получает id пользователя и
    возвращает объект рекомендации или null.*/
public interface RecommendationRuleSet {

    Optional<Recommendation> getRecommendations(UUID users_id);

}
