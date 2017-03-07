/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactlistmvc;

import com.mycompany.contactlistmvc.dao.ContactListDao;
import com.mycompany.contactlistmvc.dao.SearchTerm;
import com.mycompany.contactlistmvc.dto.Contact;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Brandon
 */
public class ContactListDaoTest {

    private ContactListDao dao;
    private Contact nc1;
    private Contact nc2;
    private Contact nc3;

    public ContactListDaoTest() {
    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("contactListDao", ContactListDao.class);

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");

        cleaner.execute("delete from contacts");

        nc1 = new Contact();

        nc1.setFirstName("Jimmy");
        nc1.setLastName("Smith");
        nc1.setCompany("Sun");
        nc1.setEmail("jimmy@smith.com");
        nc1.setPhone("1112223333");

        nc2 = new Contact();
        nc2.setFirstName("John");
        nc2.setLastName("Jones");
        nc2.setCompany("Apple");
        nc2.setEmail("john@jones.com");
        nc2.setPhone("5556667777");

        nc3 = new Contact();
        nc3.setFirstName("Steve");
        nc3.setLastName("Smith");
        nc3.setCompany("Microsoft");
        nc3.setEmail("steve@msft.com");
        nc3.setPhone("5552221234");

    }

    @After
    public void tearDown() {
    }

    /*
     TODO add test methods here.
     The methods must be annotated with annotation @Test. For example:
     */
    @Test
    public void hello() {
    }

    @Test
    public void addGetDeleteContact() {
        dao.addContact(nc1);
        Contact fromDb = dao.getContactById(nc1.getContactId());
        assertEquals(fromDb, nc1);

        dao.removeContact(nc1.getContactId());
        assertNull(dao.getContactById(nc1.getContactId()));
    }

    @Test
    public void addUpdateContact() {
        dao.addContact(nc1);
        nc1.setPhone("9999999999");
        dao.updateContact(nc1);
        Contact fromDb = dao.getContactById(nc1.getContactId());
        assertEquals(fromDb, nc1);

    }

    @Test
    public void getAllContacts() {

        dao.addContact(nc1);
        dao.addContact(nc2);

        List<Contact> cList = dao.getAllContacts();
        assertEquals(cList.size(), 2);
    }

    @Test
    public void searchContacts() {

        dao.addContact(nc1);
        dao.addContact(nc2);
        dao.addContact(nc3);

        Map<SearchTerm, String> criteria = new HashMap<>();

        criteria.put(SearchTerm.LAST_NAME, "Jones");

        List<Contact> cList = dao.searchContacts(criteria);

        assertEquals(1, cList.size());
        assertEquals(nc2, cList.get(0));

        // New search criteria - look for Smith
        criteria.put(SearchTerm.LAST_NAME, "Smith");
        cList = dao.searchContacts(criteria);
        assertEquals(2, cList.size());

        // Add company to search criteria
        criteria.put(SearchTerm.COMPANY, "Sun");
        cList = dao.searchContacts(criteria);
        assertEquals(1, cList.size());
        assertEquals(nc1, cList.get(0));

        // Change company to Microsoft, should get back nc3
        criteria.put(SearchTerm.COMPANY, "Microsoft");
        cList = dao.searchContacts(criteria);
        assertEquals(1, cList.size());
        assertEquals(nc3, cList.get(0));

        // Change company to Apple, should get back nothing
        criteria.put(SearchTerm.COMPANY, "Apple");
        cList = dao.searchContacts(criteria);
        assertEquals(0, cList.size());
    }
}