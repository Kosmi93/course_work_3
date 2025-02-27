package com.TeamToWin.course_work.repository;

import com.TeamToWin.course_work.dto.Stats;
import com.TeamToWin.course_work.model.Stat;
import com.TeamToWin.course_work.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RuleRepository {
    private final JdbcTemplate jdbcTemplate;

    public RuleRepository(@Qualifier("defaultJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public RecommendationRule addRecommendations(RecommendationRule recommendationRule) {

        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN ( " +
                        "SELECT COUNT(*) FROM recommendations t " +
                        "WHERE t.product_ID = ? " +

                        ") > 0 then 'true' else 'false' end",
                Boolean.class,
                recommendationRule.getProductId());
        if (!result) {
            String insertIntoRecommendation = "INSERT into recommendations (product_id,product_name,product_text) VALUES(?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(new PreparedStatementCreator() {
                                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                        PreparedStatement ps = connection.prepareStatement(insertIntoRecommendation, new String[]{"id"});
                                        ps.setObject(1, recommendationRule.getProductId());
                                        ps.setString(2, recommendationRule.getProductName());
                                        ps.setString(3, recommendationRule.getProductText());
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
        } else {
            return null;
        }
    }

    public List<RecommendationRule> getRecommendations() {
        List<RecommendationRule> recommendationRule = jdbcTemplate.query(
                "select id,product_id as productId,product_name as productName," +
                        "product_text as productText  from recommendations",
                (resultSet, rowNum) -> {
                    RecommendationRule rR = new RecommendationRule();
                    rR.setId(resultSet.getLong("id"));
                    rR.setProductId(UUID.fromString(resultSet.getString("productId")));
                    rR.setProductName(resultSet.getString("productName"));
                    rR.setProductText(resultSet.getString("productText"));
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
                    rule.setArguments((List<String>) List.of(resultSet.getString("arguments").split(",\\s*")));
                    rule.setNegate(resultSet.getBoolean("negate"));
                    return rule;
                }, id);

        return rules;
    }

    public List<RecommendationRule> getRecommendationRule() {
        List<RecommendationRule> recommendationRule = getRecommendations();
        List<Rule> rulesList;
        for (RecommendationRule recommendationsRule : recommendationRule) {
            rulesList = getRules(recommendationsRule.getId());
            recommendationsRule.setRule(rulesList);
        }
        return recommendationRule;
    }

    public void deleteRecommendation(UUID productId) {
        jdbcTemplate.update("delete from " +
                "rules where id in (select ru.id from rules ru " +
                "inner join recommendations re on ru.recommendation_id = re.id " +
                "and re.product_id = ? " +
                ")", productId);
        jdbcTemplate.update("DELETE FROM recommendations where product_id = ?", productId);
    }



    public Stats getStats() {
        List<Stat> stats = jdbcTemplate.query(
                "select product_id ,count from recommendations",
                (resultSet, rowNum) -> {
                    Stat stat = new Stat();
                    stat.setRule_id(resultSet.getString("product_id"));
                    stat.setCount(resultSet.getInt("count"));
                    return stat;
                });

        return new Stats(stats);
    }

    public void updateStats(UUID productId) {
        jdbcTemplate.update("UPDATE recommendations SET count = count + 1 where product_id = ? ",productId);
    }


}
