package com.TeamToWin.course_work.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RuleMapper implements RowMapper<Rule> {
    @Override
    public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rule rule = new Rule();
        rule.setId(rs.getLong("id"));
        rule.setQuery(Query.valueOf(rs.getString("query")));
        rule.setArguments((List<String>) List.of(rs.getString("arguments" ).split(",\\s*" )));
        rule.setNegate(rs.getBoolean("negate"));
        return rule;
    }

}
