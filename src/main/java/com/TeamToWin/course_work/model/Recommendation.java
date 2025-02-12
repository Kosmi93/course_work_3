package com.TeamToWin.course_work.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/*Класс рекомендация,  состоящий из полей id, «имя» и «текст».*/

public class Recommendation {
    private UUID id;
    private String productName;
    private String productText;

    public Recommendation() {
    }

    public Recommendation(UUID id, String productName, String productText) {
        this.id = id;
        this.productName = productName;
        this.productText = productText;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", product_name='" + productName + '\'' +
                ", product_text='" + productText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(productText, that.productText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productText);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }
}
