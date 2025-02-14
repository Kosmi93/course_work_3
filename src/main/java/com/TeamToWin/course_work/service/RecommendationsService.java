package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import com.TeamToWin.course_work.rule.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис рекомендаций, который содержит один метод,
 * в котором мы, по id пользователя,  возвращаем список рекомендаций для этого пользователя
 * (в зависимости от того, подошел ли какой-то продукт пользователю,
 * список может быть и пустым, и наполненным рекомендациями)
 */
@Service
public class RecommendationsService {

    private List<RecommendationRuleSet> recommendationsRuleSetList;

    public RecommendationsService(List<RecommendationRuleSet> recommendationsRuleSetList) {
        this.recommendationsRuleSetList = recommendationsRuleSetList;
    }

    public Optional<UserRecommendation> getRecommendations(UUID usersId) {
        List<Recommendation> recommendationsList = new ArrayList<>();
        for (RecommendationRuleSet recommendationRuleSet : recommendationsRuleSetList) {
            Optional<Recommendation> recommendations = recommendationRuleSet.getRecommendations(usersId);
            if (recommendations.isPresent()) {
                recommendationsList.add(recommendations.get());
            }
        }
        return Optional.of(new UserRecommendation(usersId,recommendationsList));
    }

}
