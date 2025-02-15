package com.TeamToWin.course_work.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RecommendationRule {
    private Long id;
    private String productName;
    private UUID productId;
    private String productText;
    private List<Rule> rule;

    public RecommendationRule() {
    }

    public RecommendationRule(Long id, String productName, UUID productId, String productText, List<Rule> rule) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.rule = rule;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationRule that = (RecommendationRule) o;
        return Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(productId, that.productId) && Objects.equals(productText, that.productText) && Objects.equals(rule, that.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productId, productText, rule);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<Rule> getRule() {
        return rule;
    }

    public void setRule(List<Rule> rule) {
        this.rule = rule;
    }
}
