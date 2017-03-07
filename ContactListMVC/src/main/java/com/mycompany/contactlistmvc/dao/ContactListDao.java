/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactlistmvc.dao;

import com.mycompany.contactlistmvc.dto.CompanyContactCount;
import com.mycompany.contactlistmvc.dto.Contact;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Brandon
 */
public interface ContactListDao {
    
    // add the given Contact to the data store
    public Contact addContact(Contact contact);
    
    // remove the Contact with the given id from the data store
    public void removeContact(int contactId);
    
    // update the given Contact in the data store
    public void updateContact(Contact contact);
    
    // retrieve all Contacts from the data store
    public List<Contact> getAllContacts();
    
    // retrieve the Contact with the given id from the data store
    public Contact getContactById(int contactId);
    
    // search for Contacts by the given search criteria values
    public List<Contact> searchContacts(Map<SearchTerm, String> criteria);
    
    public List<CompanyContactCount> getCompanyContactCounts();
}
