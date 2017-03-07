/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.contactlistmvc.dto;

import java.util.Objects;

/**
 *
 * @author Brandon
 */
public class CompanyContactCount {

    private String company;
    private int numContacts;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getNumContacts() {
        return numContacts;
    }

    public void setNumContacts(int numContacts) {
        this.numContacts = numContacts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.company);
        hash = 67 * hash + this.numContacts;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompanyContactCount other = (CompanyContactCount) obj;
        if (this.numContacts != other.numContacts) {
            return false;
        }
        if (!Objects.equals(this.company, other.company)) {
            return false;
        }
        return true;
    }
    
    
}
