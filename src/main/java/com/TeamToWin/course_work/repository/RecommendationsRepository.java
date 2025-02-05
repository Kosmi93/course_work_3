package com.TeamToWin.course_work.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {


    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

// Вариант 1 - несколько запросов
    public boolean haveProductType(UUID user_id,String type_product) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(distinct T.user_id) from TRANSACTIONS T " +
                        "inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "where T.USER_ID = ?  and P.TYPE = ?", Integer.class,user_id,type_product);

        return result > 0 ? true : false;
    }

    public Long getSumAmount(UUID user_id,String type_product,String type_transaction) {
        Long result = jdbcTemplate.queryForObject("SELECT SUM (amount) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? AND p.TYPE = ? AND t.TYPE = ?",
                Long.class, user_id,type_product,type_transaction);

        return result != null ? result : 0;
    }

    // Вариант 2 - один запрос
    public boolean haveInvest500(UUID user_id1,UUID user_id2) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(distinct T.user_id) from TRANSACTIONS t " +
                        "inner join products p " +
                        "where p.type = 'DEBIT' " +
                        "    and p.type != 'INVEST' " +
                        "  and t.USER_ID = ? " +
                        "and (select sum(t.AMOUNT) from TRANSACTIONS t " +
                        "    inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "    where p.type = 'SAVING' and t.TYPE = 'DEPOSIT' " +
                        "        and t.USER_ID = ?) > 1000", Integer.class,user_id1,user_id2);

        return result > 0 ? true : false;
    }
    public boolean haveTopSaving(UUID user_id1,UUID user_id2,UUID user_id3,UUID user_id4,UUID user_id5) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(distinct T.user_id) from TRANSACTIONS t " +
                        "inner join products p " +
                        "where p.type = 'DEBIT' " +
                        "  and t.USER_ID = ? " +
                        "  and ( ((select sum(t.AMOUNT) from TRANSACTIONS t " +
                        "        inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "         where p.type = 'DEBIT' and t.TYPE = 'DEPOSIT' " +
                        "         and t.USER_ID = ?) >= 50000) " +
                        " " +
                        "        or " +
                        "        ((select sum(t.AMOUNT) from TRANSACTIONS t " +
                        "                                          inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "            where p.type = 'SAVING' and t.TYPE = 'DEPOSIT' " +
                        "              and t.USER_ID = ?) >= 50000) " +
                        "      ) " +
                        "and ((select sum(t.AMOUNT) from TRANSACTIONS t " +
                        "                                    inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "      where p.type = 'DEBIT' and t.TYPE = 'DEPOSIT' " +
                        "        and t.USER_ID = ?) " +
                        "    >(select sum(t.AMOUNT) from TRANSACTIONS t " +
                        "                                    inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "      where p.type = 'DEBIT' and t.TYPE = 'WITHDRAW' " +
                        "        and t.USER_ID = ?) " +
                        " " +
                        "    );", Integer.class,user_id1,user_id2,user_id3,user_id4,user_id5);

        return result > 0 ? true : false;
    }
    public boolean haveCreditSimple(UUID user_id1,UUID user_id2,UUID user_id3,UUID user_id4) {
        Integer result = jdbcTemplate.queryForObject(
                "select count(distinct t.USER_ID) from TRANSACTIONS t " +
                        "                                   inner join products p " +
                        "where p.type != 'CREDIT' " +
                        "  and t.USER_ID = ? " +
                        "  and ((select sum(t.AMOUNT) from TRANSACTIONS t " +
                        "                                      inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "        where p.type = 'DEBIT' and t.TYPE = 'DEPOSIT' " +
                        "          and t.USER_ID = ?) " +
                        "    >(select sum(t.AMOUNT) from TRANSACTIONS t " +
                        "                                    inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "      where p.type = 'DEBIT' and t.TYPE = 'WITHDRAW' " +
                        "        and t.USER_ID = ?) " +
                        " " +
                        "    ) " +
                        "and ( " +
                        "    (select sum(t.AMOUNT) from TRANSACTIONS t " +
                        "    inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID " +
                        "     where p.type = 'DEBIT' and t.TYPE = 'WITHDRAW' " +
                        "       and t.USER_ID = ?) > 100000 " +
                        "    )", Integer.class,user_id1,user_id2,user_id3,user_id4);

        return result > 0 ? true : false;
    }


}