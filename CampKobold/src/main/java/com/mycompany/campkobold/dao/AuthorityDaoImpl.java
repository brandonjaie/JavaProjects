/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Authority;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Brandon
 */
public class AuthorityDaoImpl implements AuthorityDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_SELECT_AUTHORITY_BY_USERNAME
            = "SELECT * FROM authorities "
            + "WHERE username = ? "
            + "order by "
            + "(case when authority = 'ROLE_ADMIN' then 1 "
            + "when authority = 'ROLE_EMPLOYEE' then 2 "
            + "else 3 end) LIMIT 1";

    private static final String SQL_SELECT_AUTHORITY_BY_NAME
            = "select * from authorities where authority = ? LIMIT 1";

    @Override
    public Authority getHighestAuthorityByUserName(String userName) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_AUTHORITY_BY_USERNAME, new AuthorityMapper(), userName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Authority getAuthorityByName(String authority) {
        try {

            return jdbcTemplate.queryForObject(SQL_SELECT_AUTHORITY_BY_NAME, new AuthorityMapper(), authority);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final class AuthorityMapper implements RowMapper<Authority> {

        @Override
        public Authority mapRow(ResultSet rs, int i) throws SQLException {

            Authority authority = new Authority();
            authority.setUserName(rs.getString("username"));
            authority.setAuthority(rs.getString("authority"));

            return authority;
        }
    }

}
