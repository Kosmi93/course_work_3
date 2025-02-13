package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.model.Query;
import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.model.RecommendationRule;
import com.TeamToWin.course_work.model.Rule;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import com.TeamToWin.course_work.repository.RuleRepository;
import com.TeamToWin.course_work.rule.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Сервис рекомендаций, который содержит один метод,
 * в котором мы, по id пользователя,  возвращаем список рекомендаций для этого пользователя
 * (в зависимости от того, подошел ли какой-то продукт пользователю,
 * список может быть и пустым, и наполненным рекомендациями)
 */
@Service
public class RecommendationsService {
    private final RuleRepository ruleRepository;
    private final RecommendationsRepository recommendationRepository;

    private List<RecommendationRuleSet> recommendationsRuleSetList;

    public RecommendationsService(List<RecommendationRuleSet> recommendationsRuleSetList, RuleRepository ruleRepository,
                                  RecommendationsRepository recommendationRepository) {
        this.recommendationsRuleSetList = recommendationsRuleSetList;
        this.ruleRepository = ruleRepository;
        this.recommendationRepository = recommendationRepository;
    }

    public Optional<UserRecommendation> getRecommendations(UUID usersId) {
        List<Recommendation> recommendationsList = new ArrayList<>();
        for (RecommendationRuleSet recommendationRuleSet : recommendationsRuleSetList) {
            Optional<Recommendation> recommendations = recommendationRuleSet.getRecommendations(usersId);
            if (recommendations.isPresent()) {
                recommendationsList.add(recommendations.get());
            }
        }
        return Optional.of(new UserRecommendation(usersId, recommendationsList));
    }

    public Optional<UserRecommendation> getRecommendationsRule(UUID usersId) {

        List<Recommendation> recommendationsList = new ArrayList<>();//Список рекомендуемых продуктов
        List<RecommendationRule> recommendationsRule;
        List<Rule> rulesList;
        Recommendation recommendation;
        recommendationsRule = ruleRepository.getRecommendations();

        for (RecommendationRule recommendationRule : recommendationsRule) {
            rulesList = ruleRepository.getRules(recommendationRule.getId());
            boolean flag = false;
            int count_flag_true = 0;
            for (Rule rule : rulesList) {
                if (rule.getQuery() == Query.USER_OF) {
                    flag = recommendationRepository.checkUserOf(usersId, rule.getArguments(), rule.isNegate());
                    if (flag) {count_flag_true++;}
                } else if (rule.getQuery() == Query.ACTIVE_USER_OF) {
                    flag = recommendationRepository.checkActiveUserOf(usersId, rule.getArguments(), rule.isNegate());
                    if (flag) {count_flag_true++;}
                } else if (rule.getQuery() == Query.TRANSACTION_SUM_COMPARE) {
                    flag = recommendationRepository.checkTransactionSumCompare(usersId, rule.getArguments(), rule.isNegate());
                    if (flag) {count_flag_true++;}
                } else if (rule.getQuery() == Query.TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW) {
                    flag = recommendationRepository.checkTransactionSumCompareDepositWithdraw(usersId, rule.getArguments(), rule.isNegate());
                    if (flag) {count_flag_true++;}
                }
            }
            if (flag && (count_flag_true == rulesList.size())
            ) {
                recommendation = new Recommendation(recommendationRule.getProduct_id(),
                        recommendationRule.getProduct_name(),
                        recommendationRule.getProduct_text());
                recommendationsList.add(recommendation);
            }
        }

        return Optional.of(new UserRecommendation(usersId, recommendationsList));

    }
}
