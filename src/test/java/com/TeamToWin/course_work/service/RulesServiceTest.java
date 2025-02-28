package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.Stats;
import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.model.Query;
import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.model.RecommendationRule;
import com.TeamToWin.course_work.model.Rule;
import com.TeamToWin.course_work.repository.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(RulesService.class)
class RulesServiceTest {

    @MockBean
    private RuleRepository ruleRepository;

    @MockBean
    private RulesService rulesService;

    private RecommendationRule recommendationRule;
    private UUID productId;
    private UUID userId;
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
        Optional<UserRecommendation> userRecommendation = Optional.of(new UserRecommendation(userId, recommendation));

    }

    @Test
    void testAddRecommendation() {
        when(ruleRepository.addRecommendations(recommendationRule)).thenReturn(recommendationRule);
        RecommendationRule result = ruleRepository.addRecommendations(recommendationRule);
        assertNotNull(result);
        assertEquals(recommendationRule.getProductId(), result.getProductId());
        verify(ruleRepository, times(1)).addRecommendations(recommendationRule); // Ensure repo method is called once
    }

    @Test
    void testGetAllRecommendations() {
        List<RecommendationRule> mockRecommendationRules = new ArrayList<>();
        mockRecommendationRules.add(recommendationRule);
        when(rulesService.getAllRecommendations()).thenReturn(mockRecommendationRules);

        List<RecommendationRule> result = rulesService.getAllRecommendations();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(recommendationRule.getProductId(), result.get(0).getProductId());
    }

    @Test
    void testDeleteRecommendation() {
        doNothing().when(ruleRepository).deleteRecommendation(productId);
        ruleRepository.deleteRecommendation(productId);
        verify(ruleRepository, times(1)).deleteRecommendation(productId);
    }

    @Test
    void testGetStats() {
        Stats mockStats = new Stats();
        when(ruleRepository.getStats()).thenReturn(mockStats);

        Stats result = ruleRepository.getStats();

        assertNotNull(result);
        assertEquals(mockStats, result);
        verify(ruleRepository, times(1)).getStats();
    }
}