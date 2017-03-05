/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.dvdlibrary.dao;

/**
 *
 * @author apprentice
 */
public enum SearchTerm {

    TITLE("like"),
    RELEASE_DATE("like"),
    MPAA_RATING("like"),
    DIRECTOR("like"),
    STUDIO("like"),
    GENRE_ID("like");

    private String searchOperator;

    private SearchTerm(String searchOperator) {
        this.searchOperator = searchOperator;
    }

    public String getSearchOperator() {
        return this.searchOperator;
    }
}
