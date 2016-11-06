/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.addressbook;

import com.tsg.addressbook.dao.AddressBookDao;
import com.tsg.addressbook.dto.Address;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author apprentice
 */
@Controller
public class RESTController {

    private final AddressBookDao dao;

    @Inject
    public RESTController(AddressBookDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Address getAddress(@PathVariable("id") int id) {
        return dao.getAddressById(id);
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Address createAddress(@Valid @RequestBody Address address) {
        return dao.addAddress(address);
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable("id") int id) {
        dao.removeAddress(id);
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable("id") int id, @Valid @RequestBody Address address) {
        address.setAddressId(id);
        dao.updateAddress(address);
    }

    @RequestMapping(value = "/addresses", method = RequestMethod.GET)
    @ResponseBody
    public List<Address> getAllAddresses() {
        return dao.getAllAddresses();
    }
}
