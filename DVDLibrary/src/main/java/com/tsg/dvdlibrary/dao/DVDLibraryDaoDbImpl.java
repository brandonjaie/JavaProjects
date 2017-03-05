/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.dvdlibrary.dao;

import com.tsg.dvdlibrary.dto.DVD;
import com.tsg.dvdlibrary.dto.DVDGenreCount;
import com.tsg.dvdlibrary.dto.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author apprentice
 */
public class DVDLibraryDaoDbImpl implements DVDLibraryDao {

    private JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_DVD
            = "insert into dvd (title, release_date, mpaa_rating, director, studio, genre_id) value (?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_DVD
            = "delete from dvd where dvd_id = ?";

    private static final String SQL_UPDATE_DVD
            = "update dvd set title = ?, release_date = ?, mpaa_rating = ?, director = ?, studio = ?, genre_id = ? where dvd_id = ?";

    private static final String SQL_SELECT_DVD_BY_ID
            = "select * from dvd where dvd_id = ?";

    private static final String SQL_SELECT_GENRE_BY_GENRE_ID
            = "select * from genres where genre_id = ?";

    private static final String SQL_SELECT_GENRE_BY_GENRE_NAME
            = "select * from genres where genre_name = ?";

    private static final String SQL_SELECT_ALL_DVD
            = "select * from dvd";

    private static final String SQL_SELECT_ALL_GENRES
            = "select * from genres";

    private static final String SQL_SELECT_GENRE_DVD_COUNTS
            = "select genres.genre_name, count(*) as num_dvds"
            + " FROM genres"
            + " join dvd"
            + " on dvd.genre_id = genres.genre_id"
            + " group by genres.genre_name";

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DVD addDVD(DVD dvd) {
        jdbcTemplate.update(SQL_INSERT_DVD,
                dvd.getTitle(),
                dvd.getReleaseDate(),
                dvd.getMpaaRating(),
                dvd.getDirector(),
                dvd.getStudio(),
                dvd.getGenre().getGenreId());

        dvd.setDvdId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));

        return dvd;
    }

    @Override
    public void removeDVD(int dvdId) {
        jdbcTemplate.update(SQL_DELETE_DVD, dvdId);
    }

    @Override
    public void updateDVD(DVD dvd) {

        jdbcTemplate.update(SQL_UPDATE_DVD,
                dvd.getTitle(),
                dvd.getReleaseDate(),
                dvd.getMpaaRating(),
                dvd.getDirector(),
                dvd.getStudio(),
                dvd.getGenre().getGenreId(),
                dvd.getDvdId());
    }

    @Override
    public List<DVD> getAllDVDs() {
        return jdbcTemplate.query(SQL_SELECT_ALL_DVD, new DvdMapper());
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query(SQL_SELECT_ALL_GENRES, new GenreMapper());
    }

    @Override
    public DVD getDVDById(int dvdId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_DVD_BY_ID,
                    new DvdMapper(), dvdId);
        } catch (EmptyResultDataAccessException e) {

            return null;
        }
    }

    @Override
    public Genre getGenreById(int genreId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_GENRE_BY_GENRE_ID, new GenreMapper(), genreId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Genre getGenreByGenreName(String genreName) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_GENRE_BY_GENRE_NAME, new GenreMapper(), genreName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<DVD> searchDVDs(Map<SearchTerm, String> criteria) {

        if (criteria.size() == 0) {

            return getAllDVDs();

        } else {

            StringBuilder sQuery = new StringBuilder("select d.* "
                    + " from dvd d"
                    + " join genres g"
                    + " on d.genre_id = g.genre_id"
                    + " where d.");

            int numParams = criteria.size();

            int paramPosition = 0;

            String[] paramVals = new String[numParams];

            Set<SearchTerm> keySet = criteria.keySet();

            Iterator<SearchTerm> iter = keySet.iterator();

            while (iter.hasNext()) {

                SearchTerm currentKey = iter.next();

                if (paramPosition > 0) {

                    sQuery.append(" and d.");

                }

                sQuery.append(currentKey).append(" ").append(currentKey.getSearchOperator()).append(" ? ");

                if (currentKey.getSearchOperator().equals("like")) {
                    paramVals[paramPosition] = '%' + criteria.get(currentKey) + '%';
                } else {
                    paramVals[paramPosition] = criteria.get(currentKey);
                }

                paramPosition++;
            }

            return jdbcTemplate.query(sQuery.toString(), new DvdMapper(), paramVals);

        }
    }

    @Override
    public List<DVDGenreCount> getGenreDVDCounts() {
        return jdbcTemplate.query(SQL_SELECT_GENRE_DVD_COUNTS,
                new DVDGenreCountMapper());
    }

    private final class DvdMapper implements RowMapper<DVD> {

        @Override
        public DVD mapRow(ResultSet rs, int i) throws SQLException {
            DVD dvd = new DVD();
            dvd.setDvdId(rs.getInt("dvd_id"));
            dvd.setTitle(rs.getString("title"));
            dvd.setReleaseDate(rs.getString("release_date"));
            dvd.setMpaaRating(rs.getString("mpaa_rating"));
            dvd.setDirector(rs.getString("director"));
            dvd.setStudio(rs.getString("studio"));
            dvd.setGenre(getGenreById(rs.getInt("genre_id")));

            return dvd;
        }

    }

    private static final class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {

            Genre genre = new Genre();
            genre.setGenreId(rs.getInt("genre_id"));
            genre.setGenreName(rs.getString("genre_name"));

            return genre;
        }
    }

    private final class DVDGenreCountMapper implements RowMapper<DVDGenreCount> {

        @Override
        public DVDGenreCount mapRow(ResultSet rs, int i) throws SQLException {
            DVDGenreCount count = new DVDGenreCount();
            count.setGenre(getGenreByGenreName(rs.getString("genre_name")));
            count.setNumDVDs(rs.getInt("num_dvds"));
            return count;
        }
    }

}
