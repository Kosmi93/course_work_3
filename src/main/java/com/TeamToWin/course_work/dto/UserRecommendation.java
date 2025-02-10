package com.TeamToWin.course_work.dto;

import com.TeamToWin.course_work.model.Recommendation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class UserRecommendation {
    private UUID userId;
    private List<Recommendation> recommendations;

    public UserRecommendation(UUID usersId, List<Recommendation> recommendations) {
        this.userId = usersId;
        this.recommendations = recommendations;

    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}
