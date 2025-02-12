package com.TeamToWin.course_work.controller;

import com.TeamToWin.course_work.dto.Recommendation;
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
    private final RulesService service;

    public RulesController( RulesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Recommendation> addRule (@RequestBody Recommendation recommendation) {
        return ResponseEntity.of(service.save(recommendation));
    }

    @GetMapping
    public List<Recommendation> getAllRules (){
        return service.getAll();
    }

    @DeleteMapping("{rule_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule (@PathVariable("rule_id") UUID ruleId) {
        service.deleteById(ruleId);
    }

}
