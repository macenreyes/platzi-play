package com.platzi.play.domain.dto;

import java.time.LocalDate;
import com.platzi.play.domain.Genre;
import jakarta.validation.constraints.*;

public record MovieDto(
        Long id,

        @NotBlank(message = "El título de la película debe ser obligatorio.")
        String title,

        @Positive(message = "El título de la película debe ser superior a 0.")
        Integer duration,

        @NotNull(message = "El género debe ser incluído en la información de la película.")
        Genre gender,

        @PastOrPresent(message = "La fecha de lanzamiento de la película debe ser anterior o igual a la fecha actual.")
        LocalDate releaseDate,

        @Min(value = 0, message = "El valor mínimo de calificación debe ser cero.")
        @Max(value = 5, message = "El valor máximo de calificación debe ser cinco")
        Double rating,

        Boolean state
) {
}
