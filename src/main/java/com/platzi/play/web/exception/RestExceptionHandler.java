package com.platzi.play.web.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.platzi.play.domain.Genre;
import com.platzi.play.domain.exception.MovieAlreadyExistsException;
import com.platzi.play.domain.exception.MovieNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<Error> handleException(MovieAlreadyExistsException exception) {
        Error error = new Error("movie-already-exists", exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Error> handleException (MovieNotFoundException exception) {
        Error error = new Error("movie-not-found", exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleException(MethodArgumentNotValidException exception) {
        List<Error> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(new Error(error.getField(), error.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleException(HttpMessageNotReadableException exception) {
        String type = "format-validation-error";
        String message = "Ha ocurrido un error en el formato de los datos enviados.";

        if (exception.getCause() instanceof InvalidFormatException cause && cause.getTargetType() == Genre.class) {
            type = "genre-not-found";
            message = "El género enviado no es válido. Por favor, verifique los valores permitidos.";
        }
        Error error = new Error(type, message);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<Error> handleException(Exception exception) {
        Error error = new Error("unknown-error", exception.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }
}
