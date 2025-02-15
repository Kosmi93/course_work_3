package com.TeamToWin.course_work.model;

import java.util.List;
import java.util.UUID;

public abstract class AllQuery {
    public abstract boolean checkQuery(UUID userId, List<String> arguments, boolean negate);
}
