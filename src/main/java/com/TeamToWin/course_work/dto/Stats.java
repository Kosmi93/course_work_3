package com.TeamToWin.course_work.dto;

import com.TeamToWin.course_work.model.Stat;

import java.util.List;

public class Stats {
    private List<Stat> stats;

    public Stats() {
    }

    public Stats(List<Stat> stats) {
        this.stats = stats;
    }



    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }
}
