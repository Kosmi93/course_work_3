package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import com.TeamToWin.course_work.rule.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationsService {

    private List<RecommendationRuleSet> recommendationsRuleSetList;
    private RecommendationsRepository recommendationsRepository;

    public RecommendationsService(List<RecommendationRuleSet> recommendationsRuleSetList, RecommendationsRepository recommendationsRepository) {
        this.recommendationsRuleSetList = recommendationsRuleSetList;
        this.recommendationsRepository = recommendationsRepository;
    }

    public List<Recommendation> getRecommendations(UUID users_id) {
        List<Recommendation> recommendationsList = new ArrayList<>();

        for (RecommendationRuleSet recommendationRuleSet : recommendationsRuleSetList) {
            Optional<Recommendation> recommendations = recommendationRuleSet.getRecommendations(users_id);
            if (recommendations.isPresent()) {
                recommendationsList.add(recommendations.get());
            }
        }
        return recommendationsList;
    }
}
