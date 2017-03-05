/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.dvdlibrary.dto;

import java.util.Objects;

/**
 *
 * @author Brandon
 */
public class DVDGenreCount {

    private Genre genre;
    private int numDVDs;

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getNumDVDs() {
        return numDVDs;
    }

    public void setNumDVDs(int numDVDs) {
        this.numDVDs = numDVDs;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.genre);
        hash = 83 * hash + this.numDVDs;
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
        final DVDGenreCount other = (DVDGenreCount) obj;
        if (this.numDVDs != other.numDVDs) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        return true;
    }

}
