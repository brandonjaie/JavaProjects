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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author apprentice
 */
@Controller
public class HomeControllerNoAjax {

    private final AddressBookDao dao;

    @Inject
    public HomeControllerNoAjax(AddressBookDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/displayAddressesNoAjax", method = RequestMethod.GET)
    public String displayAddressesNoAjax(Model model) {
        List<Address> addressBook = dao.getAllAddresses();
        model.addAttribute("addresses", addressBook);
        return "displayAddressesNoAjax";
    }

    @RequestMapping(value = "/displayNewAddressFormNoAjax", method = RequestMethod.GET)
    public String displayNewAddressFormNoAjax(HttpServletRequest req, Model model) {
        
        //int addressId = Integer.parseInt(req.getParameter("addressId"));

        Address address = new Address();
        model.addAttribute("addresses", address);
        return "newAddressFormNoAjax";
    }

    @RequestMapping(value = "/addNewAddressNoAjax", method = RequestMethod.POST)
    public String addNewAddressNoAjax(@Valid @ModelAttribute("addresses") Address address, BindingResult result, HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String street = req.getParameter("street");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String zip = req.getParameter("zip");

        //Address address = new Address();
        address.setFirstName(firstName);
        address.setLastName(lastName);
        address.setStreet(street);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
        
        if(result.hasErrors()){
            return "newAddressFormNoAjax";
        }

        dao.addAddress(address);

        return "redirect:displayAddressesNoAjax";

    }

    @RequestMapping(value = "/deleteAddressNoAjax", method = RequestMethod.GET)
    public String deleteAddressNoAjax(HttpServletRequest req) {

        int addressId = Integer.parseInt(req.getParameter("addressId"));

        dao.removeAddress(addressId);

        return "redirect:displayAddressesNoAjax";
    }

    @RequestMapping(value = "/displayEditAddressFormNoAjax", method = RequestMethod.GET)
    public String displayEditAddressFormNoAjax(HttpServletRequest req, Model model) {
        
        int addressId = Integer.parseInt(req.getParameter("addressId"));

        Address address = dao.getAddressById(addressId);
        model.addAttribute("addresses", address);
        return "editAddressFormNoAjax";

    }

    @RequestMapping(value = "/editAddressNoAjax", method = RequestMethod.POST)
    public String editAddressNoAjax(@Valid @ModelAttribute("addresses") Address address, BindingResult result) {
        
        if(result.hasErrors()){
            
            return "editAddressFormNoAjax";
        }
        dao.updateAddress(address);

        return "redirect:displayAddressesNoAjax";
    }

}
