/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.vendingmachine.dao;

import com.tsg.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author Brandon
 */
public interface ItemDao {
    
    public Item addItem(Item item);
    
    public void updateItem(Item item);

    public Item getItemById(int itemId); 

    public List<Item> getAllItems();
    
    public Item vendItem(int itemId);
    
    public List<Item> stockInventory();
    
}
