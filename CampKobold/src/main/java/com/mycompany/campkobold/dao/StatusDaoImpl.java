/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

import com.mycompany.campkobold.dto.Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Brandon
 */
public class StatusDaoImpl implements StatusDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_SELECT_STATUS_BY_STATUS_ID
            = "select * from asset_statuses where status_id = ?";

    private static final String SQL_SELECT_STATUS_BY_NAME
            = "select * from asset_statuses where status = ?";

    @Override
    public Status getStatusById(int statusId) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_STATUS_BY_STATUS_ID, new StatusMapper(), statusId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Status getStatusByName(String status) {
                try {
            return jdbcTemplate.queryForObject(SQL_SELECT_STATUS_BY_NAME, new StatusMapper(), status);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final class StatusMapper implements RowMapper<Status> {

        @Override
        public Status mapRow(ResultSet rs, int i) throws SQLException {

            Status status = new Status();
            status.setStatusId(rs.getInt("status_id"));
            status.setStatus(rs.getString("status"));

            return status;
        }
    }

}
