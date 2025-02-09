package com.TeamToWin.course_work.controller;

/*
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import com.TeamToWin.course_work.rule.RecommendationRuleSet;
import com.TeamToWin.course_work.service.RecommendationsService;
*/
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import com.TeamToWin.course_work.service.RecommendationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для обработки веб-запросов
 */
@RestController
@RequestMapping("/recommendation")
public class RecommendationsController {

    private final RecommendationsRepository recommendationsRepository;
    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsRepository recommendationsRepository,
                                     RecommendationsService recommendationsService) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("{usersId}")
    public String getListOfRecommendationsForUser(@PathVariable("usersId") UUID usersId) {
        return "userId: " + usersId + ", \nrecommendations: "
                +recommendationsService.getRecommendations(usersId);
    }

}
