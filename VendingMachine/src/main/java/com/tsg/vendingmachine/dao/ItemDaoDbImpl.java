/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.vendingmachine.dao;

import com.tsg.vendingmachine.dto.Item;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brandon
 */
public class ItemDaoDbImpl implements ItemDao {

    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_ITEM
            = "insert into item (item_name, item_position, item_price, inventory, image) value (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_ITEM
            = "update item set item_name = ?, item_position = ?, item_price = ?, inventory = ?, image = ? where item_id = ?";

    private static final String SQL_SELECT_ITEM_BY_ID
            = "select * from item where item_id = ?";

    private static final String SQL_SELECT_ALL_ITEMS
            = "select * from item";

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Item addItem(Item item) {

        jdbcTemplate.update(SQL_INSERT_ITEM,
                item.getItemName(),
                item.getItemPosition(),
                item.getItemPrice(),
                item.getInventory(),
                item.getImage());

        item.setItemId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));

        return item;

    }

    @Override
    public void updateItem(Item item) {

        jdbcTemplate.update(SQL_UPDATE_ITEM,
                item.getItemName(),
                item.getItemPosition(),
                item.getItemPrice(),
                item.getInventory(),
                item.getImage(),
                item.getItemId());

    }

    @Override
    public List<Item> getAllItems() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ITEMS, new ItemMapper());
    }

    @Override
    public Item getItemById(int itemId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ITEM_BY_ID,
                    new ItemMapper(), itemId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Item vendItem(int itemId) {
        Item item = getItemById(itemId);
        item.setInventory(item.getInventory() - 1);
        updateItem(item);
        return item;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Item> stockInventory() {
        List<Item> items = getAllItems();

        for (Item item : items) {
            item.setInventory(10);
            updateItem(item);
        }

        return items;
    }

    private static final class ItemMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet rs, int i) throws SQLException {
            Item item = new Item();
            item.setItemId(rs.getInt("item_id"));
            item.setItemName(rs.getString("item_name"));
            item.setItemPosition(rs.getString("item_position"));
            item.setItemPrice(rs.getDouble("item_price"));
            item.setInventory(rs.getInt("inventory"));
            item.setImage(rs.getString("image"));

            return item;
        }

    }

}
