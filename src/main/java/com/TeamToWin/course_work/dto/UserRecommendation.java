package com.TeamToWin.course_work.dto;

import com.TeamToWin.course_work.model.Recommendation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
public class UserRecommendation {
    UUID userId;
    List<Recommendation> recommendations;

    public UserRecommendation(UUID usersId, List<Recommendation> recommendations) {
        this.userId = usersId;
        this.recommendations = recommendations;

    }
}
