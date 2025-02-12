package com.TeamToWin.course_work.dto;

import com.TeamToWin.course_work.model.Rule;

import java.util.UUID;


public class Recommendation {
    private UUID id;
    private String product_name;
    private UUID product_id;
    private String product_text;
    private Rule[] rule;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public String getProduct_text() {
        return product_text;
    }

    public void setProduct_text(String product_text) {
        this.product_text = product_text;
    }

    public Rule[] getRule() {
        return rule;
    }

    public void setRule(Rule[] rule) {
        this.rule = rule;
    }

}
