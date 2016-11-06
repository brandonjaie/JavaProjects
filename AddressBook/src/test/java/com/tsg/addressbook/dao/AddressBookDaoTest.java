/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.addressbook.dao;

import com.tsg.addressbook.dto.Address;
import com.tsg.addressbook.dto.CityAddressCount;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author apprentice
 */
public class AddressBookDaoTest {

    private AddressBookDao dao;
    private Address a1;
    private Address a2;
    private Address a3;
    private Address a4;

    public AddressBookDaoTest() {
    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("addressBookDao", AddressBookDao.class);

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");

        cleaner.execute("delete from address");

        a1 = new Address();

        a1.setFirstName("Jimmy");
        a1.setLastName("Smith");
        a1.setStreet("231 Euclid Avenue");
        a1.setCity("Cleveland");
        a1.setState("OH");
        a1.setZip("44102");

        a2 = new Address();
        a2.setFirstName("John");
        a2.setLastName("Jones");
        a2.setStreet("453 Dirt Road");
        a2.setCity("Minneapolis");
        a2.setState("MN");
        a2.setZip("55231");

        a3 = new Address();
        a3.setFirstName("Steve");
        a3.setLastName("Smith");
        a3.setStreet("123 Rodeo Drive");
        a3.setCity("Beverly Hills");
        a3.setState("CA");
        a3.setZip("90210");

        a4 = new Address();
        a4.setFirstName("Steve");
        a4.setLastName("James");
        a4.setStreet("123 Rodeo Drive");
        a4.setCity("Cleveland");
        a4.setState("OH");
        a4.setZip("44102");

    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    @Test
    public void addGetDeleteAddress() {
        dao.addAddress(a1);
        Address fromDb = dao.getAddressById(a1.getAddressId());
        assertEquals(fromDb, a1);

        dao.removeAddress(a1.getAddressId());
        assertNull(dao.getAddressById(a1.getAddressId()));
    }

    @Test
    public void addUpdateAddress() {
        dao.addAddress(a1);
        a1.setCity("Compton");
        dao.updateAddress(a1);
        Address fromDb = dao.getAddressById(a1.getAddressId());
        assertEquals(fromDb, a1);

    }

    @Test
    public void getAllAddresses() {

        dao.addAddress(a1);
        dao.addAddress(a2);

        List<Address> cList = dao.getAllAddresses();
        assertEquals(cList.size(), 2);
        assertTrue(cList.contains(a1));
        assertTrue(cList.contains(a2));
        assertFalse(cList.contains(a3));
    }

    @Test
    public void searchAddresses() {

        dao.addAddress(a1);
        dao.addAddress(a2);
        dao.addAddress(a3);

        Map<SearchTerm, String> criteria = new HashMap<>();

        criteria.put(SearchTerm.LAST_NAME, "Jones");

        List<Address> cList = dao.searchAddresses(criteria);

        assertEquals(1, cList.size());
        assertEquals(a2, cList.get(0));

        criteria.put(SearchTerm.LAST_NAME, "Smith");
        cList = dao.searchAddresses(criteria);
        assertEquals(2, cList.size());

        criteria.put(SearchTerm.STATE, "OH");
        cList = dao.searchAddresses(criteria);
        assertEquals(1, cList.size());
        assertEquals(a1, cList.get(0));

        criteria = new HashMap<>();
        criteria.put(SearchTerm.ZIP, "90210");
        cList = dao.searchAddresses(criteria);
        assertEquals(1, cList.size());
        assertEquals(a3, cList.get(0));

        criteria.put(SearchTerm.CITY, "Boise");
        cList = dao.searchAddresses(criteria);
        assertEquals(0, cList.size());
    }

    @Test
    public void getCityAddressCounts() {
        dao.addAddress(a1);
        dao.addAddress(a2);
        dao.addAddress(a3);
        dao.addAddress(a4);

        List<CityAddressCount> cList = dao.getCityAddressCounts();
        assertEquals(3, cList.size());
        assertTrue(cList.containsAll(dao.getCityAddressCounts()));
        
        Map<String, Integer> cMap = new HashMap<>();

        for (CityAddressCount count : cList) {
            if (!count.getCity().equals("")) {
                cMap.put(count.getCity(), count.getNumAddresses());
            }

            for (Map.Entry<String, Integer> entry : cMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (key.contains("Cleveland")) {
                    assertEquals(2, value);
                } else if (key.contains("Minneapolis")) {
                    assertEquals(1, value);
                } else if (key.contains("Beverly Hills")) {
                    assertEquals(1, value);
                }
            }
        }
        assertTrue(cMap.containsKey("Cleveland"));
        assertTrue(cMap.containsKey("Minneapolis"));
        assertTrue(cMap.containsKey("Beverly Hills"));
        assertFalse(cMap.containsKey("Compton"));
    }
}
