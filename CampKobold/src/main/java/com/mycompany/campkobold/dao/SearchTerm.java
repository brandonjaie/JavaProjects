/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.campkobold.dao;

/**
 *
 * @author apprentice
 */
public enum SearchTerm {
    //enumeration is a collection of contstants arranged under the same type 
    // SearchTerm is a type 
    //distinct values, contants
    //is our map key object
    ASSET_ID("arec"),
    USER_ID("up"),
    CATEGORY_ID("a"),
    STATUS_ID("arec"),
    BRAND("a"),
    DESCRIPTION("a"),
    MEMBER_ID("arec"),
    EMPLOYEE_ID("arec"),
    FIRST_NAME("up"),
    LAST_NAME("up"),
    PHONE("up"),
    EMAIL("up");
 
    
    private String alias;
    
    private SearchTerm(String alias){
        this.alias = alias;
    }
    
    public String getAlias(){
        
        return alias == null ? "": alias + ".";
    }
    
    
}
