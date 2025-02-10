package com.TeamToWin.course_work.controller;

import com.TeamToWin.course_work.dto.Rule;
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
    @PostMapping
    public ResponseEntity<Rule> addRule (@RequestMapping Rule){
        //прописать сохранение
        return ;
    }

    //поправить должен возвращать data
    @GetMapping
    public ResponseEntity<List<Rule>> getAllRules (){
        return ;
    }

    @DeleteMapping("{rule_id}")
    public void deleteRule (@PathVariable("rule_id") UUID ruleId) {
    // прописать удаление
    }

}
