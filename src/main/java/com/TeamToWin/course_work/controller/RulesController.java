package com.TeamToWin.course_work.controller;

import com.TeamToWin.course_work.model.RecommendationRule;
import com.TeamToWin.course_work.model.Rule;
import com.TeamToWin.course_work.service.RulesService;
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

/*
    @PostMapping
    public ResponseEntity<Rule> addRule (@RequestBody Rule rule){
        //прописать сохранение
        return rulesService.addRule(rule);
    }
*/
    @PostMapping
    public RecommendationRule addRecommendation (@RequestBody RecommendationRule recommendationRule){
        return rulesService.addRecommendation(recommendationRule);
    }
    //поправить должен возвращать data
/*
    @GetMapping
    public ResponseEntity<List<Rule>> getAllRules (){
        return rulesService.getAllRules();
    }
*/

    @GetMapping
    public List<RecommendationRule> getAllRecommendations (){
        return rulesService.getAllRecommendations();
    }

/*
    @DeleteMapping("{rule_id}")
    public void deleteRule (@PathVariable("rule_id") UUID ruleId) {
        rulesService.deleteRule(ruleId);
    }
*/
    @DeleteMapping("{productId}")
    public void deleteRecommendation (@PathVariable("productId") UUID productId) {
    rulesService.deleteRecommendation(productId);
}

}
