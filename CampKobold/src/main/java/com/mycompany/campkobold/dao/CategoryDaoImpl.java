/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;


import com.mycompany.campkobold.dto.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Brandon
 */
public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_SELECT_CATEGORY_BY_CATEGORY_ID
            = "select * from categories where category_id = ?";

    @Override
    public Category getCategoryById(int categoryId) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_CATEGORY_BY_CATEGORY_ID, new CategoryMapper(), categoryId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {

            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setName(rs.getString("name"));

            return category;
        }
    }

}
