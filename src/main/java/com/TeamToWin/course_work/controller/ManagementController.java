package com.TeamToWin.course_work.controller;

import com.TeamToWin.course_work.model.Info;
import com.TeamToWin.course_work.service.BuildInfoService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер для работы с кешем
 */
@RestController
@RequestMapping("/management")
public class ManagementController {

    private final BuildInfoService buildInfoService;

    public ManagementController(BuildInfoService buildInfoService) {
        this.buildInfoService = buildInfoService;
    }

    @CacheEvict(value = "getUserRecommendation", allEntries = true)
    @PostMapping("/clear-caches")
    public void clearCaches() {

    }

    @GetMapping("/info")
    public Info getInfo() {
        return buildInfoService.getBuildInfo();
    }

}
