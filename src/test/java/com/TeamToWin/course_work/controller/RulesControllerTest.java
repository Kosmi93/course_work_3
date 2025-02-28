package com.TeamToWin.course_work.controller;

import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.model.RecommendationRule;
import com.TeamToWin.course_work.service.RulesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RulesController.class)
public class RulesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RulesService rulesService;

    @InjectMocks
    private RulesController rulesController;

    @Test
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(rulesController).build();
    }

    @Test
    public void testAddRecommendation() throws Exception {
        RecommendationRule recommendationRule = new RecommendationRule();
        recommendationRule.setProductId(UUID.randomUUID());
        recommendationRule.setProductName("Sample Product");
        recommendationRule.setProductText("Description of the product");
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        List<Recommendation> recommendation = List.of(new Recommendation(userId, "test recommendation", "test recommendation"));
        Optional<UserRecommendation> userRecommendation = Optional.of(new UserRecommendation(userId, recommendation));

        when(rulesService.addRecommendation(any(RecommendationRule.class))).thenReturn(recommendationRule);

        mockMvc.perform(post("/rule")
                        .contentType("application/json")
                        .content("{ \"productId\": \"" + recommendationRule.getProductId() + "\", \"productName\": \"Sample Product\", \"productText\": \"Description of the product\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Sample Product"))
                .andExpect(jsonPath("$.productText").value("Description of the product"));

        verify(rulesService, times(1)).addRecommendation(any(RecommendationRule.class));
    }

    @Test
    public void testGetAllRecommendations() throws Exception {
        RecommendationRule recommendationRule = new RecommendationRule();
        recommendationRule.setProductId(UUID.randomUUID());
        recommendationRule.setProductName("Sample Product");
        recommendationRule.setProductText("Description of the product");

        when(rulesService.getAllRecommendations()).thenReturn(Collections.singletonList(recommendationRule));

        mockMvc.perform(get("/rule"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Sample Product"))
                .andExpect(jsonPath("$[0].productText").value("Description of the product"));

        verify(rulesService, times(1)).getAllRecommendations();
    }

    @Test
    public void testDeleteRecommendation() throws Exception {
        UUID productId = UUID.randomUUID();
        doNothing().when(rulesService).deleteRecommendation(productId);

        mockMvc.perform(delete("/rule/{productId}", productId))
                .andExpect(status().isNoContent());

        verify(rulesService, times(1)).deleteRecommendation(productId);
    }
}
