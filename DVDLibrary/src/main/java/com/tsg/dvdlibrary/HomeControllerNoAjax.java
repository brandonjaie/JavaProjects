/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.dvdlibrary;

import com.tsg.dvdlibrary.dao.DVDLibraryDao;
import com.tsg.dvdlibrary.dto.DVD;
import com.tsg.dvdlibrary.dto.Genre;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author apprentice
 */
@Controller
public class HomeControllerNoAjax {

    private final DVDLibraryDao dao;

    @Inject
    public HomeControllerNoAjax(DVDLibraryDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/displayDVDsNoAjax", method = RequestMethod.GET)
    public String displayDVDsNoAjax(Model model) {
        
        List<DVD> dList = dao.getAllDVDs();
        model.addAttribute("dvds", dList);
        
        return "displayDVDsNoAjax";
    }

    @RequestMapping(value = "/displayNewDVDFormNoAjax", method = RequestMethod.GET)
    public String displayNewDVDFormNoAjax(Model model) {

        DVD dvd = new DVD();
        model.addAttribute("dvd", dvd);

        List<Genre> genres = dao.getAllGenres();
        model.addAttribute("genres", genres);
        
        return "newDVDFormNoAjax";
    }

    @RequestMapping(value = "/addNewDVDNoAjax", method = RequestMethod.POST)
    public String addNewDVDNoAjax(Model model, @Valid @ModelAttribute("dvd") DVD dvd, BindingResult result) {

        dvd.setTitle(dvd.getTitle());
        dvd.setReleaseDate(dvd.getReleaseDate());
        dvd.setMpaaRating(dvd.getMpaaRating());
        dvd.setDirector(dvd.getDirector());
        dvd.setStudio(dvd.getStudio());
        dvd.setGenre(dvd.getGenre());

        if (result.hasErrors()) {
            List<Genre> genres = dao.getAllGenres();
            model.addAttribute("genres", genres);
            return "newDVDFormNoAjax";
        }

        dao.addDVD(dvd);

        return "redirect:displayDVDsNoAjax";

    }

    @RequestMapping(value = "/deleteDVDNoAjax", method = RequestMethod.GET)
    public String deleteDVDNoAjax(HttpServletRequest req) {

        int dvdId = Integer.parseInt(req.getParameter("dvdId"));

        dao.removeDVD(dvdId);

        return "redirect:displayDVDsNoAjax";
    }

    @RequestMapping(value = "/displayEditDVDFormNoAjax", method = RequestMethod.GET)
    public String displayEditDVDFormNoAjax(HttpServletRequest req, Model model) {
        
        int dvdId = Integer.parseInt(req.getParameter("dvdId"));

        DVD dvd = dao.getDVDById(dvdId);
        model.addAttribute("dvd", dvd);

        List<Genre> genres = dao.getAllGenres();
        model.addAttribute("genres", genres);
        return "editDVDFormNoAjax";

    }

    @RequestMapping(value = "/editDVDNoAjax", method = RequestMethod.POST)
    public String editDVDNoAjax(Model model, @Valid @ModelAttribute("dvd") DVD dvd, BindingResult result) {

        if (result.hasErrors()) {
            List<Genre> genres = dao.getAllGenres();
            model.addAttribute("genres", genres);
            return "editDVDFormNoAjax";
        }
        dao.updateDVD(dvd);

        return "redirect:displayDVDsNoAjax";
    }

}
