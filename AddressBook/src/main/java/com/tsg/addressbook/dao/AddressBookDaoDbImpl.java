/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.addressbook.dao;

import com.tsg.addressbook.dto.Address;
import com.tsg.addressbook.dto.CityAddressCount;
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
 * @author apprentice
 */
public class AddressBookDaoDbImpl implements AddressBookDao {

    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_ADDRESS
            = "insert into address (first_name, last_name, street, city, state, zip) value (?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_ADDRESS
            = "delete from address where address_id = ?";

    private static final String SQL_UPDATE_ADDRESS
            = "update address set first_name = ?, last_name = ?, street = ?, city = ?, state = ?, zip = ? where address_id = ?";

    private static final String SQL_SELECT_ADDRESS_BY_ID
            = "select * from address where address_id = ?";

    private static final String SQL_SELECT_ALL_ADDRESS
            = "select * from address";

    private static final String SQL_SELECT_CITY_ADDRESS_COUNTS
            = "select city, count(*) as num_addresses FROM address group by city;";

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Address addAddress(Address address) {

        jdbcTemplate.update(SQL_INSERT_ADDRESS,
                address.getFirstName(),
                address.getLastName(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip());

        address.setAddressId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));

        return address;

    }

    @Override
    public void removeAddress(int addressId) {
        jdbcTemplate.update(SQL_DELETE_ADDRESS, addressId);
    }

    @Override
    public void updateAddress(Address address) {

        jdbcTemplate.update(SQL_UPDATE_ADDRESS,
                address.getFirstName(),
                address.getLastName(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZip(),
                address.getAddressId());

    }

    @Override
    public List<Address> getAllAddresses() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ADDRESS, new AddressMapper());
    }

    @Override
    public Address getAddressById(int addressId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ADDRESS_BY_ID,
                    new AddressMapper(), addressId);
        } catch (EmptyResultDataAccessException e) {
            // no results for this id - just return null
            return null;
        }
    }

    @Override
    public List<Address> searchAddresses(Map<SearchTerm, String> criteria) {

        if (criteria.size() == 0) {

            return getAllAddresses();

        } else {

            StringBuilder sQuery = new StringBuilder("select * from address where ");

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

            return jdbcTemplate.query(sQuery.toString(), new AddressMapper(), paramVals);

        }
    }

    @Override
    public List<CityAddressCount> getCityAddressCounts() {
        return jdbcTemplate.query(SQL_SELECT_CITY_ADDRESS_COUNTS,
                new CityAddressCountMapper());
    }

    private static final class AddressMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(ResultSet rs, int i) throws SQLException {
            Address address = new Address();
            address.setAddressId(rs.getInt("address_id"));
            address.setFirstName(rs.getString("first_name"));
            address.setLastName(rs.getString("last_name"));
            address.setStreet(rs.getString("street"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setZip(rs.getString("zip"));

            return address;
        }

    }

    private static final class CityAddressCountMapper implements RowMapper<CityAddressCount> {

        @Override
        public CityAddressCount mapRow(ResultSet rs, int i) throws SQLException {
            CityAddressCount count = new CityAddressCount();
            count.setCity(rs.getString("city"));
            count.setNumAddresses(rs.getInt("num_addresses"));
            return count;
        }
    }

}
