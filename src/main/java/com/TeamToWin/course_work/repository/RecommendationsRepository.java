package com.TeamToWin.course_work.repository;

import com.TeamToWin.course_work.dto.UserDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public List<UserDTO> getUser(String username) {
        List<UserDTO> userDTOList = jdbcTemplate.query(
                "select * from USERS where username = ?",
                (resultSet, rowNum) -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(UUID.fromString(resultSet.getString("id")));
                    userDTO.setUsername(resultSet.getString("username"));
                    userDTO.setFirstName(resultSet.getString("first_name"));
                    userDTO.setLastName(resultSet.getString("last_name"));
                    return userDTO;
                }, username);

        return userDTOList;
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

    public boolean checkUserOf(UUID user_id, List<String> arguments, boolean negate) {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN ( " +
                        "SELECT COUNT(*) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.TYPE = '" + arguments.get(0) + "' " +
                        ") > 0 then 'true' else 'false' end",
                Boolean.class,
                user_id);
        return result == negate ? true : false;
    }
    public boolean checkActiveUserOf(UUID user_id, List<String> arguments, boolean negate) {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN ( " +
                        "SELECT COUNT(*) FROM TRANSACTIONS t " +
                        "INNER JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID " +
                        "WHERE t.USER_ID = ? " +
                        "AND p.TYPE = '" + arguments.get(0) + "' " +
                        ") >= 5 then 'true' else 'false' end",
                Boolean.class,
                user_id);
        return result == negate ? false : true;
    }

    public boolean checkTransactionSumCompare(UUID user_id, List<String> arguments, boolean negate) {
        Boolean result = jdbcTemplate.queryForObject(
                "select case when (" +
                        "    select count(distinct T.user_id)" +
                        "    from TRANSACTIONS t" +
                        "    inner join products p" +
                        "    where p.type = '" + arguments.get(0) + "'" +
                        "      and t.USER_ID = ?" +
                        "      and (select sum(t.AMOUNT)" +
                        "           from TRANSACTIONS t" +
                        "           inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID" +
                        "           where p.type = '" + arguments.get(0) + "'" +
                        "             and t.TYPE = '" + arguments.get(1) + "'" +
                        "             and t.USER_ID = ?) " + arguments.get(2) + arguments.get(3) +
                        ") > 0 then 'true' else 'false' end",
                Boolean.class,
                user_id,user_id);
        return result == negate ? true : false;
    }

    public boolean checkTransactionSumCompareDepositWithdraw(UUID user_id, List<String> arguments, boolean negate) {
        Boolean result = jdbcTemplate.queryForObject(
    "select case when (" +
        "    select count(distinct t.USER_ID)" +
        "    from TRANSACTIONS t" +
        "             inner join products p" +
        "    where p.type != '" + arguments.get(0) + "'" +
        "      and t.USER_ID = ?" +
        "      and ((select sum(t.AMOUNT)" +
        "            from TRANSACTIONS t" +
        "                     inner join PUBLIC.PRODUCTS p on P.ID = T.PRODUCT_ID" +
        "            where p.type = '" + arguments.get(0) + "'" +
        "              and t.TYPE = 'DEPOSIT'" +
        "              and t.USER_ID = ?) " +
                       arguments.get(1) + " (select sum(t.AMOUNT)" +
        "           from TRANSACTIONS t" +
        "                    inner join PUBLIC.PRODUCTS P on P.ID = T.PRODUCT_ID" +
        "           where p.type = '" + arguments.get(0) +  "'" +
        "             and t.TYPE = 'WITHDRAW'" +
        "             and t.USER_ID = ?)" +
        "        )" +
        "    ) > 0 then 'true' else 'false' end",
                Boolean.class,
                user_id,
                user_id,
                user_id);
        return result == negate ? true : false;
    }
}