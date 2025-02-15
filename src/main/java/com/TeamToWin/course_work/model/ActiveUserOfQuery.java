package com.TeamToWin.course_work.model;

import com.TeamToWin.course_work.repository.RecommendationsRepository;

import java.util.List;
import java.util.UUID;

public class ActiveUserOfQuery extends AllQuery{
    private final RecommendationsRepository recommendationRepository;

    public ActiveUserOfQuery(RecommendationsRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }
    @Override
    public boolean checkQuery(UUID userId, List<String> arguments, boolean negate) {
        return recommendationRepository.checkActiveUserOf(userId, arguments, negate);
    }

}
