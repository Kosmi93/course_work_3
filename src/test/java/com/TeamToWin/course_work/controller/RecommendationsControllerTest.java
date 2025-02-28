package com.TeamToWin.course_work.controller;

import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.model.Recommendation;
import com.TeamToWin.course_work.service.RecommendationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationsController.class)
class RecommendationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationsService recommendationsService;

    @Test
    void getListOfRecommendationsForUser() throws Exception {
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        List<Recommendation> recommendation = List.of(new Recommendation(userId, "test recommendation", "test recommendation"));
        Optional<UserRecommendation> userRecommendation = Optional.of(new UserRecommendation(userId, recommendation));

        when(recommendationsService.getRecommendationsRule(userId)).thenReturn(userRecommendation);

        mockMvc.perform(get("/recommendation/{usersId}", userId))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    assertThat(result.getResponse().getContentAsString()).contains("test recommendation");
                });
    }
}