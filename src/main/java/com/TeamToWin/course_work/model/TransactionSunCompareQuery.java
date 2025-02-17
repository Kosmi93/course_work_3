package com.TeamToWin.course_work.model;

import com.TeamToWin.course_work.repository.RecommendationsRepository;

import java.util.List;
import java.util.UUID;

public class TransactionSunCompareQuery extends AllQuery {
    private final RecommendationsRepository recommendationRepository;

    public TransactionSunCompareQuery (RecommendationsRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }
    @Override
    public boolean checkQuery(UUID userId, List<String> arguments, boolean negate) {
        return recommendationRepository.checkTransactionSumCompare(userId, arguments, negate);
    }

}
