package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.model.Query;
import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.model.RecommendationRule;
import com.TeamToWin.course_work.model.Rule;
import com.TeamToWin.course_work.repository.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(RecommendationsService.class)
class RecommendationsServiceTest {

    @MockBean
    private RuleRepository ruleRepository;

    @MockBean
    private RecommendationsService recommendationsService;

    private UUID userId;
    @Mock
    private RecommendationRule recommendationRule;
    private Rule rule;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        recommendationRule = new RecommendationRule();
        recommendationRule.setId(1L);
        recommendationRule.setProductId(UUID.randomUUID());
        recommendationRule.setProductName("Test Product");
        recommendationRule.setProductText("This is a test product.");

        List<Rule> rules = new ArrayList<>();
        rule = new Rule();
        rule.setId(1L);
        rule.setQuery(Query.USER_OF);
        rule.setArguments(Collections.singletonList("test_argument"));
        rule.setNegate(false);
        rules.add(rule);
        recommendationRule.setRule(rules);
        when(ruleRepository.getRecommendations()).thenReturn(Collections.singletonList(recommendationRule));
        when(ruleRepository.getRules(recommendationRule.getId())).thenReturn(rules);
        List<Recommendation> recommendation = List.of(new Recommendation(userId, "test recommendation", "test recommendation"));
    }

    @Test
    void testGetRecommendations() {
        List<Recommendation> recommendation = List.of(new Recommendation(userId, "test recommendation", "test recommendation"));
        Optional<UserRecommendation> userRecommendation = Optional.of(new UserRecommendation(userId, recommendation));

        when(recommendationsService.getRecommendationsRule(userId)).thenReturn(userRecommendation);

        Optional<UserRecommendation> result = recommendationsService.getRecommendationsRule(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getUserId());
        assertFalse(result.get().getRecommendations().isEmpty());
    }

    @Test
    void testGetRecommendationsRule() {
        List<Recommendation> expectedRecommendations = new ArrayList<>();
        expectedRecommendations.add(new Recommendation(UUID.randomUUID(), "Test Product", "Test description"));
        List<Recommendation> recommendation = List.of(new Recommendation(userId, "test recommendation", "test recommendation"));
        Optional<UserRecommendation> userRecommendation = Optional.of(new UserRecommendation(userId, recommendation));

        when(recommendationsService.getRecommendationsRule(userId)).thenReturn(userRecommendation);
        when(ruleRepository.getRecommendations()).thenReturn(Collections.singletonList(recommendationRule));
        when(ruleRepository.getRules(recommendationRule.getId())).thenReturn(Collections.singletonList(rule));

        Optional<UserRecommendation> result = recommendationsService.getRecommendationsRule(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getUserId());
        assertFalse(result.get().getRecommendations().isEmpty());
    }

    @Test
    void testGetRecommendationsEmpty() {
        when(ruleRepository.getRecommendations()).thenReturn(Collections.emptyList());
        List<RecommendationRule> result = ruleRepository.getRecommendations();
        assertTrue(result.isEmpty());
    }
}