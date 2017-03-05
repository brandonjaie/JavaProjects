/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.dvdlibrary.dao;

import com.tsg.dvdlibrary.dto.DVD;
import com.tsg.dvdlibrary.dto.DVDGenreCount;
import com.tsg.dvdlibrary.dto.Genre;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author apprentice
 */
public class DVDLibraryDaoTest {

    private DVDLibraryDao dao;
    private DVD d1;
    private DVD d2;
    private DVD d3;

    public DVDLibraryDaoTest() {
    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("dvdLibraryDao", DVDLibraryDao.class);

        JdbcTemplate cleaner = (JdbcTemplate) ctx.getBean("jdbcTemplate");

        cleaner.execute("delete from dvd");
        
        cleaner.execute("ALTER TABLE dvd auto_increment = 1");

        d1 = new DVD();

        d1.setDvdId(1);
        d1.setTitle("SpaceBalls");
        d1.setReleaseDate("1979");
        d1.setMpaaRating("PG");
        d1.setDirector("Mel Brooks");
        d1.setStudio("MiraMax");
        d1.setGenre(dao.getGenreById(8));

        d2 = new DVD();

        d2.setDvdId(2);
        d2.setTitle("The Warriors");
        d2.setReleaseDate("1979");
        d2.setMpaaRating("R");
        d2.setDirector("Walter Hill");
        d2.setStudio("Paramount");
        d2.setGenre(dao.getGenreById(1));

        d3 = new DVD();

        d3.setDvdId(3);
        d3.setTitle("Star Wars");
        d3.setReleaseDate("1979");
        d3.setMpaaRating("PG");
        d3.setDirector("George Lucas");
        d3.setStudio("LucasFilm");
        d3.setGenre(dao.getGenreById(8));

    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void testAddGetDVDByIdDeleteDVDs() {
        dao.addDVD(d1);
        DVD fromDb = dao.getDVDById(d1.getDvdId());
        assertEquals(fromDb.getDvdId(), d1.getDvdId());

        dao.removeDVD(d1.getDvdId());
        assertNull(dao.getDVDById(d1.getDvdId()));
    }

    @Test
    public void testAddUpdateDVDs() {
        dao.addDVD(d1);
        DVD fromDb = dao.getDVDById(d1.getDvdId());
        assertEquals(fromDb.getMpaaRating(), "PG");
        d1.setMpaaRating("PG-13");
        dao.updateDVD(d1);
        DVD fromDb2 = dao.getDVDById(d1.getDvdId());
        assertEquals(fromDb2.getMpaaRating(), "PG-13");

    }

    @Test
    public void testGetAllDVDs() {

        dao.addDVD(d1);
        dao.addDVD(d2);

        List<DVD> cList = dao.getAllDVDs();
        assertEquals(cList.size(), 2);
    }

    @Test
    public void testGetAllGenres() {

        dao.addDVD(d1);
        dao.addDVD(d2);

        List<Genre> gList = dao.getAllGenres();
        assertEquals(gList.size(), 8);

    }

    @Test
    public void testGetDVDbyId() {

        dao.addDVD(d3);
        
        DVD fromDb = dao.getDVDById(d3.getDvdId());
        
        assertEquals(fromDb.getDvdId(), d3.getDvdId());
        

    }

    @Test
    public void testGetGenreById() {

        dao.addDVD(d1);

        Genre fromDb = dao.getGenreById(d1.getGenre().getGenreId());

        assertEquals(fromDb.getGenreId(), 8);

    }

    @Test
    public void testGetGenreByName() {

        dao.addDVD(d2);

        Genre fromDb = dao.getGenreByGenreName(d2.getGenre().getGenreName());

        assertEquals(fromDb.getGenreName(), "ACTION");

    }

    @Test
    public void searchDVDs() {

        dao.addDVD(d1);
        dao.addDVD(d2);
        dao.addDVD(d3);

        DVD fromDb = dao.getDVDById(d1.getDvdId());

        Map<SearchTerm, String> criteria = new HashMap<>();

        criteria.put(SearchTerm.TITLE, "Spaceballs");

        List<DVD> dList = dao.searchDVDs(criteria);

        assertEquals(1, dList.size());

        Boolean exists = false;

        for (DVD dvd : dList) {
            if (dvd.getTitle().equals(fromDb.getTitle())) {
                exists = true;
            }
        }

        assertTrue(exists);

        //partial search test
        DVD fromDb2 = dao.getDVDById(d2.getDvdId());

        Map<SearchTerm, String> criteria2 = new HashMap<>();

        criteria2.put(SearchTerm.STUDIO, "para");

        List<DVD> dList2 = dao.searchDVDs(criteria2);

        assertEquals(1, dList2.size());

        Boolean exists2 = false;

        for (DVD dvd : dList2) {
            if (dvd.getStudio().equals(fromDb2.getStudio())) {
                exists2 = true;
            }
        }

        assertTrue(exists2);

        DVD fromDb3 = dao.getDVDById(d3.getDvdId());

        Map<SearchTerm, String> criteria3 = new HashMap<>();

        criteria3.put(SearchTerm.GENRE_ID, Integer.toString(fromDb3.getGenre().getGenreId()));

        List<DVD> dList3 = dao.searchDVDs(criteria3);

        assertEquals(2, dList3.size());

        Boolean exists3 = false;

        for (DVD dvd : dList3) {
            if (dvd.getGenre().getGenreId() == fromDb3.getGenre().getGenreId()) {
                exists3 = true;
            }
        }

        assertTrue(exists3);
    }

    @Test
    public void testGetGenreDVDCounts() {

        dao.addDVD(d1);
        dao.addDVD(d2);
        dao.addDVD(d3);

        List<DVDGenreCount> cList = dao.getGenreDVDCounts();
        assertEquals(2, cList.size());
        assertTrue(cList.containsAll(dao.getGenreDVDCounts()));

        Map<String, Integer> cMap = new HashMap<>();

        for (DVDGenreCount count : cList) {
            if (!count.getGenre().getGenreName().equals("")) {
                cMap.put(count.getGenre().getGenreName(), count.getNumDVDs());
            }

            for (Map.Entry<String, Integer> entry : cMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (key.contains("SCI-FI")) {
                    assertEquals(2, value);
                } else if (key.contains("ACTION")) {
                    assertEquals(1, value);
                }
            }
        }
        assertTrue(cMap.containsKey("SCI-FI"));
        assertTrue(cMap.containsKey("ACTION"));
        assertFalse(cMap.containsKey("DRAMA"));

    }
}
