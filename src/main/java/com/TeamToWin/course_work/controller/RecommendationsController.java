package com.TeamToWin.course_work.controller;


import com.TeamToWin.course_work.dto.UserRecommendation;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import com.TeamToWin.course_work.service.RecommendationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Контроллер для обработки веб-запросов
 */
@RestController
@RequestMapping("/recommendation")
public class RecommendationsController {


    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {

        this.recommendationsService = recommendationsService;
    }

    @GetMapping("{usersId}")
    public Optional<UserRecommendation> getListOfRecommendationsForUser(@PathVariable("usersId") UUID usersId) {
        return recommendationsService.getRecommendationsRule(usersId);
    }

}
