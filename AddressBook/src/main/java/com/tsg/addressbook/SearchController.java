 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.addressbook;

import com.tsg.addressbook.dao.AddressBookDao;
import com.tsg.addressbook.dao.SearchTerm;
import com.tsg.addressbook.dto.Address;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author apprentice
 */
@Controller
public class SearchController {

    private final AddressBookDao dao;

    @Inject
    public SearchController(AddressBookDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String displaySearchPage() {
        return "search";
    }

    @RequestMapping(value = "search/addresses", method = RequestMethod.POST)
    @ResponseBody
    public List<Address> searchAddresses(@RequestBody Map<String, String> searchMap) {
        Map<SearchTerm, String> criteriaMap = new HashMap<>();

        String currentTerm = searchMap.get("firstName");
        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.FIRST_NAME, currentTerm);
        }
        currentTerm = searchMap.get("lastName");

        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.LAST_NAME, currentTerm);
        }
        currentTerm = searchMap.get("street");
        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.STREET, currentTerm);
        }
        currentTerm = searchMap.get("city");
        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.CITY, currentTerm);
        }
        currentTerm = searchMap.get("state");
        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.STATE, currentTerm);
        }
        currentTerm = searchMap.get("zip");
        if (!currentTerm.isEmpty()) {
            criteriaMap.put(SearchTerm.ZIP, currentTerm);
        }

        return dao.searchAddresses(criteriaMap);
    }
}
