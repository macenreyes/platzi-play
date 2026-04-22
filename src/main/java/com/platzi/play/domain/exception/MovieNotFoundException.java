package com.platzi.play.domain.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(long movieId) {
        super("La película no existe");
    }
}
