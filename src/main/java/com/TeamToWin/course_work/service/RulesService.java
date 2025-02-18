package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.UserDTO;
import com.TeamToWin.course_work.model.RecommendationRule;
import com.TeamToWin.course_work.model.Rule;
import com.TeamToWin.course_work.repository.RuleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RulesService {

    private final RuleRepository ruleRepository;

    public RulesService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }


    public RecommendationRule addRecommendation(RecommendationRule recommendationRule) {
        return ruleRepository.addRecommendations(recommendationRule);

    }

    public List<RecommendationRule> getAllRecommendations() {
        return  ruleRepository.getRecommendationRule();
    }

    public void deleteRecommendation(UUID productId) {
        ruleRepository.deleteRecommendation(productId);
    }


}
