/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.dvdlibrary.dao;

import com.tsg.dvdlibrary.dto.DVD;
import com.tsg.dvdlibrary.dto.DVDGenreCount;
import com.tsg.dvdlibrary.dto.Genre;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author apprentice
 */
public class DVDLibraryDaoInMemImpl implements DVDLibraryDao {

    private Map<Integer, DVD> dvdMap = new HashMap<>();

    private static int dvdIdCounter = 0;

    @Override
    public DVD addDVD(DVD dvd) {
        dvd.setDvdId(dvdIdCounter);
        dvdIdCounter++;
        dvdMap.put(dvd.getDvdId(), dvd);
        return dvd;
    }

    @Override
    public void removeDVD(int dvdId) {
        dvdMap.remove(dvdId);
    }

    @Override
    public void updateDVD(DVD dvd) {
        dvdMap.put(dvd.getDvdId(), dvd);
    }

    @Override
    public List<DVD> getAllDVDs() {
        Collection<DVD> d = dvdMap.values();
        return new ArrayList(d);
    }

    @Override
    public DVD getDVDById(int dvdId) {
        return dvdMap.get(dvdId);
    }

    @Override
    public List<DVD> searchDVDs(Map<SearchTerm, String> criteria) {

        String titleCriteria = criteria.get(SearchTerm.TITLE);
        String releaseDateCriteria = criteria.get(SearchTerm.RELEASE_DATE);
        String mpaaRatingCriteria = criteria.get(SearchTerm.MPAA_RATING);
        String directorCriteria = criteria.get(SearchTerm.DIRECTOR);
        String studioCriteria = criteria.get(SearchTerm.STUDIO);

        Predicate<DVD> titleMatches;

        Predicate<DVD> releaseDateMatches;

        Predicate<DVD> mpaaRatingMatches;

        Predicate<DVD> directorMatches;

        Predicate<DVD> studioMatches;

        Predicate<DVD> truePredicate = (c) -> {
            return true;
        };

        titleMatches = (titleCriteria == null || titleCriteria.isEmpty()) ? truePredicate : (c) -> c.getTitle().equalsIgnoreCase(titleCriteria);
        releaseDateMatches = (releaseDateCriteria == null || releaseDateCriteria.isEmpty()) ? truePredicate : (c) -> ("" + c.getReleaseDate()).equalsIgnoreCase(releaseDateCriteria);
        mpaaRatingMatches = (mpaaRatingCriteria == null || mpaaRatingCriteria.isEmpty()) ? truePredicate : (c) -> c.getMpaaRating().equalsIgnoreCase(mpaaRatingCriteria);
        directorMatches = (directorCriteria == null || directorCriteria.isEmpty()) ? truePredicate : (c) -> c.getDirector().equalsIgnoreCase(directorCriteria);
        studioMatches = (studioCriteria == null || studioCriteria.isEmpty()) ? truePredicate : (c) -> c.getStudio().equalsIgnoreCase(studioCriteria);

        return dvdMap.values().stream()
                .filter(titleMatches
                        .and(releaseDateMatches)
                        .and(mpaaRatingMatches)
                        .and(directorMatches)
                        .and(studioMatches))
                .collect(Collectors.toList());

    }

    @Override
    public Genre getGenreById(int genreId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DVDGenreCount> getGenreDVDCounts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Genre getGenreByGenreName(String genreName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Genre> getAllGenres() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
