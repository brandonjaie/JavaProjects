/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.dvdlibrary.dao;

import com.tsg.dvdlibrary.dto.DVD;
import com.tsg.dvdlibrary.dto.DVDGenreCount;
import com.tsg.dvdlibrary.dto.Genre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public interface DVDLibraryDao {
    
    public DVD addDVD(DVD dvd);
    
    public void removeDVD(int dvdId);
    
    public void updateDVD(DVD dvd);
    
    public List<DVD> getAllDVDs();
    
    public List<Genre> getAllGenres();
    
    public DVD getDVDById(int dvdId);
    
    public Genre getGenreById(int genreId);
    
    public Genre getGenreByGenreName(String genreName);
    
    public List<DVD> searchDVDs(Map<SearchTerm, String> criteria);
    
    public List<DVDGenreCount> getGenreDVDCounts();
    
    
    
}
