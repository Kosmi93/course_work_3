package com.TeamToWin.course_work.rule;

import com.TeamToWin.course_work.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {

    Optional<Recommendation> getRecommendations(UUID users_id);

}
