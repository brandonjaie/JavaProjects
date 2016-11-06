/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.addressbook.dao;

import com.tsg.addressbook.dto.Address;
import com.tsg.addressbook.dto.CityAddressCount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class AddressBookDaoInMemImpl implements AddressBookDao {

    private Map<Integer, Address> addressMap = new HashMap<>();
    private static int addressIdCounter = 0;

    @Override
    public Address addAddress(Address address) {
        address.setAddressId(addressIdCounter);
        addressIdCounter++;
        addressMap.put(address.getAddressId(), address);
        return address;
    }

    @Override
    public void removeAddress(int addressId) {
        addressMap.remove(addressId);
    }

    @Override
    public void updateAddress(Address address) {
        addressMap.put(address.getAddressId(), address);
    }

    @Override
    public List<Address> getAllAddresses() {
        Collection<Address> a = addressMap.values();
        return new ArrayList(a);
    }

    @Override
    public Address getAddressById(int addressId) {
        return addressMap.get(addressId);
    }

    @Override
    public List<Address> searchAddresses(Map<SearchTerm, String> criteria) {

        String firstNameCriteria = criteria.get(SearchTerm.FIRST_NAME);
        String lastNameCriteria = criteria.get(SearchTerm.LAST_NAME);
        String streetCriteria = criteria.get(SearchTerm.STREET);
        String cityCriteria = criteria.get(SearchTerm.CITY);
        String stateCriteria = criteria.get(SearchTerm.STATE);
        String zipCriteria = criteria.get(SearchTerm.ZIP);

        Predicate<Address> firstNameMatches;

        Predicate<Address> lastNameMatches;

        Predicate<Address> streetMatches;

        Predicate<Address> cityMatches;

        Predicate<Address> stateMatches;

        Predicate<Address> zipMatches;

        Predicate<Address> truePredicate = (c) -> {
            return true;
        };

        firstNameMatches = (firstNameCriteria == null || firstNameCriteria.isEmpty()) ? truePredicate : (c) -> c.getFirstName().equalsIgnoreCase(firstNameCriteria);
        lastNameMatches = (lastNameCriteria == null || lastNameCriteria.isEmpty()) ? truePredicate : (c) -> c.getLastName().equalsIgnoreCase(lastNameCriteria);
        streetMatches = (streetCriteria == null || streetCriteria.isEmpty()) ? truePredicate : (c) -> c.getStreet().equalsIgnoreCase(streetCriteria);
        cityMatches = (cityCriteria == null || cityCriteria.isEmpty()) ? truePredicate : (c) -> c.getCity().equalsIgnoreCase(cityCriteria);
        stateMatches = (stateCriteria == null || stateCriteria.isEmpty()) ? truePredicate : (c) -> c.getState().equalsIgnoreCase(stateCriteria);
        zipMatches = (zipCriteria == null || zipCriteria.isEmpty()) ? truePredicate : (c) -> c.getZip().equals(zipCriteria);

        return addressMap.values().stream()
                .filter(firstNameMatches
                        .and(lastNameMatches)
                        .and(streetMatches)
                        .and(cityMatches)
                        .and(stateMatches)
                        .and(zipMatches))
                .collect(Collectors.toList());

    }

    @Override
    public List<CityAddressCount> getCityAddressCounts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
