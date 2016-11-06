/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.addressbook.dto;

import java.util.Objects;

/**
 *
 * @author Brandon
 */
public class CityAddressCount {

    private String city;
    private int numAddresses;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumAddresses() {
        return numAddresses;
    }

    public void setNumAddresses(int numAddresses) {
        this.numAddresses = numAddresses;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.city);
        hash = 29 * hash + this.numAddresses;
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
        final CityAddressCount other = (CityAddressCount) obj;
        if (this.numAddresses != other.numAddresses) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        return true;
    }
    
    

}
