/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.vendingmachine.dao;

import com.tsg.vendingmachine.dto.Item;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Brandon
 */
public class ItemDaoMemImpl implements ItemDao {

    Map<Integer, Item> itemMap = new HashMap<>();
    private static int itemIdCounter = 0;

    public ItemDaoMemImpl() {
        Item i1 = new Item();
        i1.setItemId(1);
        i1.setItemPosition("A1");
        i1.setItemName("Ruffles");
        i1.setItemPrice(0.75);
        i1.setInventory(7);
        i1.setImage("./img/ruffles.jpg");

        Item i2 = new Item();
        i2.setItemId(2);
        i2.setItemPosition("A2");
        i2.setItemName("Doritos");
        i2.setItemPrice(0.75);
        i2.setInventory(9);
        i2.setImage("./img/doritos.jpg");

        Item i3 = new Item();
        i3.setItemId(3);
        i3.setItemPosition("A3");
        i3.setItemName("Funyuns");
        i3.setItemPrice(0.75);
        i3.setInventory(10);
        i3.setImage("./img/funyuns.jpg");

        Item i4 = new Item();
        i4.setItemId(4);
        i4.setItemPosition("B1");
        i4.setItemName("Coke");
        i4.setItemPrice(0.85);
        i4.setInventory(5);
        i4.setImage("./img/coke.png");

        Item i5 = new Item();
        i5.setItemId(5);
        i5.setItemPosition("B2");
        i5.setItemName("Cherry Coke");
        i5.setItemPrice(0.85);
        i5.setInventory(6);
        i5.setImage("./img/cherrycoke.png");

        Item i6 = new Item();
        i6.setItemId(6);
        i6.setItemPosition("B3");
        i6.setItemName("Diet Coke");
        i6.setItemPrice(0.85);
        i6.setInventory(11);
        i6.setImage("./img/dietcoke.png");

        Item i7 = new Item();
        i7.setItemId(7);
        i7.setItemPosition("C1");
        i7.setItemName("Peanuts");
        i7.setItemPrice(0.95);
        i7.setInventory(10);
        i7.setImage("./img/peanuts.jpg");

        Item i8 = new Item();
        i8.setItemId(8);
        i8.setItemPosition("C2");
        i8.setItemName("Trail Mix");
        i8.setItemPrice(0.95);
        i8.setInventory(8);
        i8.setImage("./img/trailmix.jpg");

        Item i9 = new Item();
        i9.setItemId(9);
        i9.setItemPosition("C3");
        i9.setItemName("Honey Roasted");
        i9.setItemPrice(0.95);
        i9.setInventory(12);
        i9.setImage("./img/honeyroasted.jpg");

        itemMap.put(i1.getItemId(), i1);
        itemMap.put(i2.getItemId(), i2);
        itemMap.put(i3.getItemId(), i3);
        itemMap.put(i4.getItemId(), i4);
        itemMap.put(i5.getItemId(), i5);
        itemMap.put(i6.getItemId(), i6);
        itemMap.put(i7.getItemId(), i7);
        itemMap.put(i8.getItemId(), i8);
        itemMap.put(i9.getItemId(), i9);
    }

    @Override
    public Item addItem(Item item) {
        item.setItemId(itemIdCounter);
        itemIdCounter++;
        itemMap.put(item.getItemId(), item);
        return item;
    }

    @Override
    public void updateItem(Item item) {
        itemMap.put(item.getItemId(), item);
    }

    @Override
    public Item getItemById(int itemId) {
        return itemMap.get(itemId);
    }

    @Override
    public List<Item> getAllItems() {
        Collection<Item> items = itemMap.values();
        return new ArrayList(items);
    }

    @Override
    public Item vendItem(int itemId) {
        Item item = getItemById(itemId);
        item.setInventory(item.getInventory() - 1);
        updateItem(item);
        return item;
    }

    //DB Implementation Only
    @Override
    public List<Item> stockInventory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
