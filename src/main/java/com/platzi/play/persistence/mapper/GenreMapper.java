package com.platzi.play.persistence.mapper;

import com.platzi.play.domain.Genre;
import org.mapstruct.Named;

public class GenreMapper {
    @Named("stringToGenre")
    public static Genre stringToGenre(String genre) {
        if (genre == null) return null;
        return switch (genre.toUpperCase()) {
            case "TERROR" -> Genre.HORROR;
            case "CIENCIA FICCION" -> Genre.SCI_FI;
            case "ACCION" -> Genre.ACTION;
            case "COMEDIA" -> Genre.COMEDY;
            case "DRAMA" -> Genre.DRAMA;
            case "ANIMADA" -> Genre.ANIMATED;
            default -> null;
        };
    }

    @Named("genreToString")
    public static String genreToString(Genre genre) {
        if (genre == null) return null;
        return switch (genre) {
            case HORROR -> "TERROR";
            case DRAMA ->  "DRAMA";
            case ACTION ->  "ACCION";
            case COMEDY ->  "COMEDIA";
            case SCI_FI ->  "CIENCIA FICCION";
            case ANIMATED ->   "ANIMADA";
            default -> null;
        };
    }
}
