package com.TeamToWin.course_work.repository;
import com.TeamToWin.course_work.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RuleRepository{
    private final JdbcTemplate jdbcTemplate;

    public RuleRepository(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RecommendationRule addRecommendations(RecommendationRule recommendationRule) {

        String insertIntoRecommendation = "INSERT into recommendations (product_id,product_name,product_text) VALUES(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                    PreparedStatement ps = connection.prepareStatement(insertIntoRecommendation, new String[]{"id"});
                                    ps.setObject(1, recommendationRule.getProduct_id());
                                    ps.setString(2, recommendationRule.getProduct_name());
                                    ps.setString(3, recommendationRule.getProduct_text());
                                    return ps;
                                }
                            },
                keyHolder);

        String insertIntoRule = "INSERT into rules (query, arguments, negate,recommendation_id) VALUES(?,?,?,?)";
        KeyHolder keyHolderRule = new GeneratedKeyHolder();
        List<Rule> rules = recommendationRule.getRule();

        for (int i = 0; i < rules.size(); i++) {

            Query query = rules.get(i).getQuery();
            String arguments = rules.get(i).getArguments()
                                            .stream()
                                            .collect(Collectors.joining(", "));
            boolean negate = rules.get(i).isNegate();

            jdbcTemplate.update(new PreparedStatementCreator() {
                                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                        PreparedStatement ps = connection.prepareStatement(insertIntoRule, new String[]{"id"});
                                        ps.setString(1, String.valueOf(query));
                                        ps.setString(2, arguments);
                                        ps.setBoolean(3, negate);
                                        ps.setObject(4, keyHolder.getKey());
                                        return ps;
                                    }
                                },
                    keyHolderRule);
            recommendationRule.getRule().get(i).setId(keyHolderRule.getKey().longValue());
        }
        recommendationRule.setId(keyHolderRule.getKey().longValue());
        return recommendationRule;
    }
    public List<RecommendationRule> listRecommendations() {
        List<RecommendationRule> recommendationRule = jdbcTemplate.query(
                "select id,product_id as productId,product_name as productName," +
                        "product_text as productText  from recommendations",
                (resultSet, rowNum) -> {
                    RecommendationRule rR = new RecommendationRule();
                    rR.setId(resultSet.getLong("id"));
                    rR.setProduct_id(UUID.fromString(resultSet.getString("productId")));
                    rR.setProduct_name(resultSet.getString("productName"));
                    rR.setProduct_text(resultSet.getString("productText"));
                    return rR;
                });

        return recommendationRule;
    }

    public List<Rule> getRules(Long id) {
        List<Rule> rules = jdbcTemplate.query(
                "select id,query,arguments,negate from rules where recommendation_id = ?",
                (resultSet, rowNum) -> {
                    Rule rule = new Rule();
                    rule.setId(resultSet.getLong("id"));
                    rule.setQuery(Query.valueOf(resultSet.getString("query")));
                    rule.setArguments((List<String>) List.of(resultSet.getString("arguments" ).split(",\\s*" )));
                    rule.setNegate(resultSet.getBoolean("negate"));
                    return rule;
                },id);

        return rules;
    }
}
