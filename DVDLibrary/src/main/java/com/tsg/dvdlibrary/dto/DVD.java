/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsg.dvdlibrary.dto;

import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author apprentice
 */
public class DVD {

    private int dvdId;

    @NotEmpty(message = "You must supply a value for Title.")
    @Length(max = 50, message = "Title must be no longer to 50 characters in length.")
    private String title;

    @NotEmpty(message = "You must supply a value for Release Date.")
    @Length(min = 4, max = 8, message = "Release Date must be no less than 4 numbers or no more than 8 numbers in length.")
    private String releaseDate;

    @NotEmpty(message = "You must supply a value for MPAA Rating.")
    @Length(min = 1, max = 5, message = "MPAA must be no less than 1 character or no more than 5 characters in length.")
    private String mpaaRating;

    @NotEmpty(message = "You must supply a value for Director.")
    @Length(max = 50, message = "Director must be no longer to 50 characters in length.")
    private String director;

    @NotEmpty(message = "You must supply a value for Studio.")
    @Length(max = 50, message = "Studio must be no longer to 50 characters in length.")
    private String studio;

    @Valid
    @NotNull
    private Genre genre;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.dvdId;
        hash = 31 * hash + Objects.hashCode(this.title);
        hash = 31 * hash + Objects.hashCode(this.releaseDate);
        hash = 31 * hash + Objects.hashCode(this.mpaaRating);
        hash = 31 * hash + Objects.hashCode(this.director);
        hash = 31 * hash + Objects.hashCode(this.studio);
        hash = 31 * hash + Objects.hashCode(this.genre);
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
        final DVD other = (DVD) obj;
        if (this.dvdId != other.dvdId) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.mpaaRating, other.mpaaRating)) {
            return false;
        }
        if (!Objects.equals(this.director, other.director)) {
            return false;
        }
        if (!Objects.equals(this.studio, other.studio)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        if (!Objects.equals(this.releaseDate, other.releaseDate)) {
            return false;
        }
        return true;
    }

    public int getDvdId() {
        return dvdId;
    }

    public void setDvdId(int dvdId) {
        this.dvdId = dvdId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

}
