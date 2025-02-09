package com.TeamToWin.course_work.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для выполнения запросов в БД.
 */
@Repository
public class RecommendationsRepository {


    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean haveProductType(UUID userId,String typeProduct) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(distinct T.user_id) from TRANSACTIONS T " +
                        "inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "where T.USER_ID = ?  and P.TYPE = ?", Integer.class,userId,typeProduct);

        return result > 0 ? true : false;
    }

    public Long getSumAmount(UUID userId,String typeProduct,String typeTransaction) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = ?",
                Long.class, userId,typeProduct,typeTransaction);

        return result != null ? result : 0;
    }

}