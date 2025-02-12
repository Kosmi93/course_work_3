package com.TeamToWin.course_work.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

//@Entity
public class Rule {
    private Long id;
//    @Enumerated(EnumType.STRING)
    private Query query;
//    @ElementCollection
    private List<String> arguments;
    private boolean negate;
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private Recommendations recommendations;


    public Rule() {
    }

    public Rule(Query query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return negate == rule.negate && Objects.equals(id, rule.id) && query == rule.query && Objects.equals(arguments, rule.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, query, arguments, negate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

}
