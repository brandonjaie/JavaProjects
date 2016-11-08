/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.vendingmachine.dao;

import com.tsg.vendingmachine.dto.Item;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Brandon
 */
public class ItemDaoDbImplTest {

    private ItemDao iDao;

    private Item i1;
    private Item i2;
    private Item i3;
    private Item i4;
    private Item i5;
    private Item i6;
    private Item i7;
    private Item i8;
    private Item i9;

    public ItemDaoDbImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        iDao = ctx.getBean("ItemDao", ItemDao.class);

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");

        cleaner.execute("delete from item");

        i1 = new Item();
        i1.setItemPosition("A1");
        i1.setItemName("Ruffles");
        i1.setItemPrice(0.75);
        i1.setInventory(10);
        i1.setImage("./img/ruffles.jpg");

        i2 = new Item();
        i2.setItemPosition("A2");
        i2.setItemName("Doritos");
        i2.setItemPrice(0.75);
        i2.setInventory(10);
        i2.setImage("./img/doritos.jpg");

        i3 = new Item();
        i3.setItemPosition("A3");
        i3.setItemName("Funyuns");
        i3.setItemPrice(0.75);
        i3.setInventory(10);
        i3.setImage("./img/funyuns.jpg");

        i4 = new Item();
        i4.setItemPosition("B1");
        i4.setItemName("Coke");
        i4.setItemPrice(0.85);
        i4.setInventory(10);
        i4.setImage("./img/coke.png");

        i5 = new Item();
        i5.setItemPosition("B2");
        i5.setItemName("Cherry Coke");
        i5.setItemPrice(0.85);
        i5.setInventory(10);
        i5.setImage("./img/cherrycoke.png");

        i6 = new Item();
        i6.setItemPosition("B3");
        i6.setItemName("Diet Coke");
        i6.setItemPrice(0.85);
        i6.setInventory(10);
        i6.setImage("./img/dietcoke.png");

        i7 = new Item();
        i7.setItemPosition("C1");
        i7.setItemName("Peanuts");
        i7.setItemPrice(0.95);
        i7.setInventory(10);
        i7.setImage("./img/peanuts.jpg");

        i8 = new Item();
        i8.setItemPosition("C2");
        i8.setItemName("Trail Mix");
        i8.setItemPrice(0.95);
        i8.setInventory(10);
        i8.setImage("./img/trailmix.jpg");

        i9 = new Item();
        i9.setItemPosition("C3");
        i9.setItemName("Honey Roasted");
        i9.setItemPrice(0.95);
        i9.setInventory(10);
        i9.setImage("./img/honeyroasted.jpg");

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllItems method and getItemById method, of class
     * ItemDaoDbImpl.
     */
    @Test
    public void testAddItemANDGetAllItemsANDGetItemById() {
        List<Item> emptyList = iDao.getAllItems();
        assertEquals(0, emptyList.size());

        iDao.addItem(i1);
        iDao.addItem(i2);
        iDao.addItem(i3);
        iDao.addItem(i4);
        iDao.addItem(i5);
        iDao.addItem(i6);
        iDao.addItem(i7);
        iDao.addItem(i8);
        iDao.addItem(i9);

        List<Item> iList = iDao.getAllItems();
        assertEquals(9, iList.size());
        assertTrue(iList.contains(i5));
        assertTrue(iList.contains(i9));

        Item item = iDao.getItemById(i1.getItemId());
        assertTrue(item.getItemName().equals("Ruffles"));

        item = iDao.getItemById(i2.getItemId());
        assertTrue(item.getItemName().equals("Doritos"));

    }

    /**
     * Test of updateItem method, of class ItemDaoDbImpl.
     */
    @Test
    public void testUpdateItem() {
        iDao.addItem(i1);

        Item item = iDao.getItemById(i1.getItemId());
        assertTrue(item.getInventory() == 10);

        item.setInventory(15);

        iDao.updateItem(item);

        Item fromDb = iDao.getItemById(i1.getItemId());

        assertEquals(fromDb.getInventory(), 15);
    }

    /**
     * Test of vendItem method, of class ItemDaoDbImpl.
     */
    @Test
    public void testVendItem() {
        iDao.addItem(i1);
        iDao.addItem(i2);
        iDao.addItem(i3);

        assertTrue(i1.getInventory() == 10);
        assertTrue(i2.getInventory() == 10);
        assertTrue(i3.getInventory() == 10);

        Item fromDB = iDao.vendItem(i1.getItemId());
        Item fromDB1 = iDao.vendItem(i2.getItemId());
        Item fromDB2 = iDao.vendItem(i3.getItemId());

        assertEquals(fromDB.getInventory(), 9);
        assertEquals(fromDB1.getInventory(), 9);
        assertEquals(fromDB2.getInventory(), 9);
    }

    /**
     * Test of stockInventory method, of class ItemDaoDbImpl.
     */
    @Test
    public void testStockInventory() {

        iDao.addItem(i1);
        iDao.addItem(i2);
        iDao.addItem(i3);

        assertTrue(i1.getInventory() == 10);
        assertTrue(i2.getInventory() == 10);
        assertTrue(i3.getInventory() == 10);

        Item fromDB = iDao.vendItem(i1.getItemId());
        Item fromDB1 = iDao.vendItem(i2.getItemId());
        Item fromDB2 = iDao.vendItem(i3.getItemId());

        assertEquals(fromDB.getInventory(), 9);
        assertEquals(fromDB1.getInventory(), 9);
        assertEquals(fromDB2.getInventory(), 9);

        iDao.stockInventory();
        
        Item fromDB4 = iDao.getItemById(i1.getItemId());
        Item fromDB5 = iDao.getItemById(i2.getItemId());
        Item fromDB6 = iDao.getItemById(i3.getItemId());

        assertEquals(fromDB4.getInventory(), 10);
        assertEquals(fromDB5.getInventory(), 10);
        assertEquals(fromDB6.getInventory(), 10);

    }
}
