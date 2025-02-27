package com.TeamToWin.course_work.controller;

import com.TeamToWin.course_work.model.Info;
import com.TeamToWin.course_work.model.RecommendationRule;
import com.TeamToWin.course_work.repository.RecommendationsRepository;
import com.TeamToWin.course_work.repository.RuleRepository;
import com.TeamToWin.course_work.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @CacheEvict(value = "getUserRecommendation", allEntries = true)
    @PostMapping("/clear-caches")
    public void clearCaches() {
        managerService.clearCaches();
    }

    @GetMapping("/info")
    public Info getInfoService() {
        return managerService.getInfo();
    }
}
