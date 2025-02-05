package com.TeamToWin.course_work.model;

import java.util.Objects;
import java.util.UUID;

public class Recommendation {
    private UUID id;
    private String product_name;
    private String product_text;

    public Recommendation(UUID id, String product_name, String product_text) {
        this.id = id;
        this.product_name = product_name;
        this.product_text = product_text;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", product_text='" + product_text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(id, that.id) && Objects.equals(product_name, that.product_name) && Objects.equals(product_text, that.product_text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product_name, product_text);
    }

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

    public String getProduct_text() {
        return product_text;
    }

    public void setProduct_text(String product_text) {
        this.product_text = product_text;
    }
}
