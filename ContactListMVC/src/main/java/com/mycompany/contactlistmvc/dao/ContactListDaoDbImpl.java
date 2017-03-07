/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactlistmvc.dao;

import com.mycompany.contactlistmvc.dto.CompanyContactCount;
import com.mycompany.contactlistmvc.dto.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Brandon
 */

/*
Notes:
    1. All SQL code must be in the form of Prepared Statements. This helps prevent SQL injection attacks
        against our application.
    2. Spring JdbcTemplate:
        a. Declare a reference to a JdbcTemplate.
        b. Create setter for JdbcTemplate so we can use Spring Setter Injection to set the reference (this
            is configured in the spring-persistence.xml file in the following section).
    3. Wrap addContact in a transaction so that we are guaranteed that the insert statement and the
        LAST_INSERT_ID call use the same connection. If different connections are used for these queries in
        a high-concurrency environment (i.e. many users using the database at the same time), it is possible to
        get the wrong value for the last inserted id.
    4. Any time we get values back from the database, we must provide an instance of a class that knows
        how to map database rows into model objects. In our case, we pass an instance of the ContactMapper
        class.
    5. The ContactMapper class knows how to map rows from the contacts table to properties on the Contact
        model object. 
 */
public class ContactListDaoDbImpl implements ContactListDao {

    // #1 - All SQL code is in the form of Prepared Statements
    private static final String SQL_INSERT_CONTACT
            = "insert into contacts (first_name, last_name, company, phone, email) values (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_CONTACT
            = "delete from contacts where contact_id = ?";

    private static final String SQL_UPDATE_CONTACT
            = "update contacts set first_name = ?, last_name = ?, company = ?, phone = ?, email = ? where contact_id = ?";

    private static final String SQL_SELECT_ALL_CONTACTS
            = "select * from contacts";

    private static final String SQL_SELECT_CONTACT
            = "select * from contacts where contact_id = ?";

    private static final String SQL_SELECT_COMPANY_CONTACT_COUNTS
            = "SELECT company, count(*) as num_contacts FROM contacts group by company;";

    // #2a - Declare JdbcTemplate reference - the instance will be handed to us by Spring
    private JdbcTemplate jdbcTemplate;
    // #2b - We are using Setter Injection to direct Spring to hand us an instance of
    // the JdbcTemplate (see the Spring Configuration section below for configuration
    // details).

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    // #3 - Wrap addContact in a transaction so the we are guaranteed to get the
    // correct LAST_INSERT_ID for our row.
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Contact addContact(Contact contact) {
        jdbcTemplate.update(SQL_INSERT_CONTACT,
                contact.getFirstName(),
                contact.getLastName(),
                contact.getCompany(),
                contact.getPhone(),
                contact.getEmail());
        contact.setContactId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
        return contact;
    }

    @Override
    public void removeContact(int contactId) {
        jdbcTemplate.update(SQL_DELETE_CONTACT, contactId);
    }

    @Override
    public void updateContact(Contact contact) {
        jdbcTemplate.update(SQL_UPDATE_CONTACT,
                contact.getFirstName(),
                contact.getLastName(),
                contact.getCompany(),
                contact.getPhone(),
                contact.getEmail(),
                contact.getContactId());
    }

    @Override
    // #4 - getAllContacts, getContactById
    // class to map the rows from the database into Contact objects
    public List<Contact> getAllContacts() {
        return jdbcTemplate.query(SQL_SELECT_ALL_CONTACTS, new ContactMapper());
    }

    @Override
    public Contact getContactById(int contactId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_CONTACT,
                    new ContactMapper(), contactId);
        } catch (EmptyResultDataAccessException ex) {
            // there were no results for the given contact id - we just want to
            // return null in this case
            return null;
        }
    }

    @Override
    public List<Contact> searchContacts(Map<SearchTerm, String> criteria) {

        if (criteria.size() == 0) {

            return getAllContacts();

        } else {

            StringBuilder sQuery = new StringBuilder("select * from contacts where ");

            int numParams = criteria.size();

            int paramPosition = 0;

            String[] paramVals = new String[numParams];

            Set<SearchTerm> keySet = criteria.keySet();

            Iterator<SearchTerm> iter = keySet.iterator();

            while (iter.hasNext()) {

                SearchTerm currentKey = iter.next();

                if (paramPosition > 0) {

                    sQuery.append(" and ");

                }

                sQuery.append(currentKey);

                sQuery.append(" = ? ");

                paramVals[paramPosition] = criteria.get(currentKey);

                paramPosition++;

            }

            return jdbcTemplate.query(sQuery.toString(), new ContactMapper(), paramVals);

        }

    }

    @Override
    public List<CompanyContactCount> getCompanyContactCounts() {
        return jdbcTemplate.query(SQL_SELECT_COMPANY_CONTACT_COUNTS,
                new CompanyContactCountMapper());
    }

    // #5 - This class maps the columns in the 'contacts’ table into properties on the
    // Contact object
    private static final class ContactMapper implements RowMapper<Contact> {

        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contact contact = new Contact();
            contact.setContactId(rs.getInt("contact_id"));
            contact.setFirstName(rs.getString("first_name"));
            contact.setLastName(rs.getString("last_name"));
            contact.setCompany(rs.getString("company"));
            contact.setPhone(rs.getString("phone"));
            contact.setEmail(rs.getString("email"));
            return contact;
        }
    }

    private static final class CompanyContactCountMapper
            implements RowMapper<CompanyContactCount> {

        @Override
        public CompanyContactCount mapRow(ResultSet rs, int i) throws SQLException {
            CompanyContactCount count = new CompanyContactCount();
            count.setCompany(rs.getString("company"));
            count.setNumContacts(rs.getInt("num_contacts"));
            return count;
        }
    }
}
