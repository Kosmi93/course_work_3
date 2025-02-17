package com.TeamToWin.course_work.controller;

import com.TeamToWin.course_work.model.RecommendationRule;
import com.TeamToWin.course_work.model.Rule;
import com.TeamToWin.course_work.service.RulesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


/**
 * Контроллер для добавления правил
 */
@RestController
@RequestMapping("/rule")
public class RulesController {
    private final RulesService rulesService;
    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @PostMapping
    public RecommendationRule addRecommendation (@RequestBody RecommendationRule recommendationRule){
        return rulesService.addRecommendation(recommendationRule);
    }
    //поправить должен возвращать data

    @GetMapping
    public List<RecommendationRule> getAllRecommendations (){
        return rulesService.getAllRecommendations();
    }


    @DeleteMapping("{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecommendation (@PathVariable("productId") UUID productId) {
    rulesService.deleteRecommendation(productId);
}

}
