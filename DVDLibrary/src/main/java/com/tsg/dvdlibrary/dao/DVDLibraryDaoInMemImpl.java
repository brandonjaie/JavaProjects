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

    private Map<Integer, Genre> genreMap = new HashMap<>();

    public DVDLibraryDaoInMemImpl() {
        Genre g1 = new Genre();
        g1.setGenreId(1);
        g1.setGenreName("ACTION");
        genreMap.put(1, g1);

        Genre g2 = new Genre();
        g2.setGenreId(2);
        g2.setGenreName("CLASSIC");
        genreMap.put(2, g2);

        Genre g3 = new Genre();
        g3.setGenreId(3);
        g3.setGenreName("COMEDY");
        genreMap.put(3, g3);

        Genre g4 = new Genre();
        g4.setGenreId(4);
        g4.setGenreName("DOCUMENTARY");
        genreMap.put(4, g4);

        Genre g5 = new Genre();
        g5.setGenreId(5);
        g5.setGenreName("DRAMA");
        genreMap.put(5, g5);

        Genre g6 = new Genre();
        g6.setGenreId(6);
        g6.setGenreName("ROMANCE");
        genreMap.put(6, g6);

        Genre g7 = new Genre();
        g7.setGenreId(7);
        g7.setGenreName("THRILLER");
        genreMap.put(7, g7);

        Genre g8 = new Genre();
        g8.setGenreId(8);
        g8.setGenreName("SCI-FI");
        genreMap.put(8, g8);
    }

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
    public List<Genre> getAllGenres() {
        Collection<Genre> g = genreMap.values();
        return new ArrayList(g);
    }

    @Override
    public DVD getDVDById(int dvdId) {
        return dvdMap.get(dvdId);
    }

    @Override
    public Genre getGenreById(int genreId) {
        return genreMap.get(genreId);
    }

    @Override
    public Genre getGenreByGenreName(String genreName) {
        Collection<Genre> g = genreMap.values();
        Genre match = new Genre();
        for (Genre genre : g) {
            if (genre.getGenreName().equals(genreName)) {
                match = getGenreById(genre.getGenreId());
            }
        }
        return match;
    }

    @Override
    public List<DVD> searchDVDs(Map<SearchTerm, String> criteria) {

        String titleCriteria = criteria.get(SearchTerm.TITLE);
        String releaseDateCriteria = criteria.get(SearchTerm.RELEASE_DATE);
        String mpaaRatingCriteria = criteria.get(SearchTerm.MPAA_RATING);
        String directorCriteria = criteria.get(SearchTerm.DIRECTOR);
        String studioCriteria = criteria.get(SearchTerm.STUDIO);
        String genreCriteria = criteria.get(SearchTerm.GENRE_ID);

        Predicate<DVD> titleMatches;
        Predicate<DVD> releaseDateMatches;
        Predicate<DVD> mpaaRatingMatches;
        Predicate<DVD> directorMatches;
        Predicate<DVD> studioMatches;
        Predicate<DVD> genreMatches;
        Predicate<DVD> truePredicate = (c) -> {
            return true;
        };

        titleMatches
                = (titleCriteria == null || titleCriteria.isEmpty())
                        ? truePredicate : (c) -> c.getTitle().toLowerCase().contains(titleCriteria.toLowerCase());

        releaseDateMatches
                = (releaseDateCriteria == null || releaseDateCriteria.isEmpty())
                        ? truePredicate : (c) -> ("" + c.getReleaseDate()).toLowerCase().contains(releaseDateCriteria.toLowerCase());

        mpaaRatingMatches
                = (mpaaRatingCriteria == null || mpaaRatingCriteria.isEmpty())
                        ? truePredicate : (c) -> c.getMpaaRating().toLowerCase().contains(mpaaRatingCriteria.toLowerCase());

        directorMatches
                = (directorCriteria == null || directorCriteria.isEmpty())
                        ? truePredicate : (c) -> c.getDirector().toLowerCase().contains(directorCriteria.toLowerCase());

        studioMatches
                = (studioCriteria == null || studioCriteria.isEmpty())
                        ? truePredicate : (c) -> c.getStudio().toLowerCase().contains(studioCriteria.toLowerCase());

        genreMatches
                = (genreCriteria == null || genreCriteria.isEmpty())
                        ? truePredicate : (c) -> Integer.toString(c.getGenre().getGenreId()).equals(genreCriteria);

        return dvdMap.values().stream()
                .filter(titleMatches
                        .and(releaseDateMatches)
                        .and(mpaaRatingMatches)
                        .and(directorMatches)
                        .and(studioMatches)
                        .and(genreMatches))
                .collect(Collectors.toList());

    }

    @Override
    public List<DVDGenreCount> getGenreDVDCounts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
