package com.TeamToWin.course_work.service;

import com.TeamToWin.course_work.dto.Recommendation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RulesService {
    public List<Recommendation> getAll() {
        return null;
    }

    public void deleteById(UUID ruleId) {
    }

    public Optional<Recommendation> save(Recommendation recommendation) {
        return Optional.empty();
    }
}
