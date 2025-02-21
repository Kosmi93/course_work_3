package com.TeamToWin.course_work.model;

public class Stat {
    private String rule_id;
    private Integer count;

    public Stat() {
    }

    public Stat(String rule_id, Integer count) {
        this.rule_id = rule_id;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRule_id() {
        return rule_id;
    }

    public void setRule_id(String rule_id) {
        this.rule_id = rule_id;
    }
}
