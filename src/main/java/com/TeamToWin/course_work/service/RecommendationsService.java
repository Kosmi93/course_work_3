package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.UserDTO;
import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.model.*;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import com.TeamToWin.course_work.repository.RuleRepository;
import com.TeamToWin.course_work.rule.RecommendationRuleSet;
import org.springframework.cache.annotation.Cacheable;
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
    Map<Query, AllQuery> queryMap= new HashMap<Query, AllQuery>();

    public RecommendationsService(List<RecommendationRuleSet> recommendationsRuleSetList, RuleRepository ruleRepository,
                                  RecommendationsRepository recommendationRepository) {
        this.recommendationsRuleSetList = recommendationsRuleSetList;
        this.ruleRepository = ruleRepository;
        this.recommendationRepository = recommendationRepository;
        AllQuery userOfQuery = new UserOfQuery(recommendationRepository);
        AllQuery activeUserOfQuery = new ActiveUserOfQuery(recommendationRepository);
        AllQuery sumCompareDWQuery = new SumCompareDWQuery(recommendationRepository);
        AllQuery transactionSumCompareQuery = new TransactionSunCompareQuery(recommendationRepository);
        queryMap.put(Query.USER_OF, userOfQuery);
        queryMap.put(Query.ACTIVE_USER_OF, activeUserOfQuery);
        queryMap.put(Query.TRANSACTION_SUM_COMPARE, transactionSumCompareQuery);
        queryMap.put(Query.TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW, sumCompareDWQuery);

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
    @Cacheable("getUserRecommendation")
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
                 flag = queryMap.get(rule.getQuery()).checkQuery(usersId, rule.getArguments(), rule.isNegate());
                if (flag) {
                    count_flag_true++;
                } else {
                    break;
                }

            }
            if (flag && (count_flag_true == rulesList.size())
            ) {
                recommendation = new Recommendation(recommendationRule.getProductId(),
                        recommendationRule.getProductName(),
                        recommendationRule.getProductText());
                recommendationsList.add(recommendation);
                ruleRepository.updateStats(recommendation.getId());
            }
        }

        return Optional.of(new UserRecommendation(usersId, recommendationsList));

    }

    public  List<UserDTO> getUser(String userName) { return recommendationRepository.getUser(userName); }
}
