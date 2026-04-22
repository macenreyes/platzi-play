package com.platzi.play.web.controller;

import com.platzi.play.domain.dto.MovieDto;
import com.platzi.play.domain.dto.SuggestRequestDto;
import com.platzi.play.domain.dto.UpdateMovieDto;
import com.platzi.play.domain.service.MovieService;
import com.platzi.play.domain.service.PlatziPlayAIService;
import dev.langchain4j.service.UserMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "Operations about movies of platzi-play")
public class MovieController {
    private final MovieService movieService;
    private final PlatziPlayAIService platziPlayAIService;

    public MovieController(MovieService movieService, PlatziPlayAIService platziPlayAIService) {
        this.movieService = movieService;
        this.platziPlayAIService = platziPlayAIService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() {

        return ResponseEntity.ok(this.movieService.getAll());
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Get a movie by id.",
            description = "Return the movie selected.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Movie found."),
                    @ApiResponse(responseCode = "404", description = "Movie not found.", content = @Content)
            }
    )
    public ResponseEntity<MovieDto> getById(@Parameter(description = "Identificador de la película a recuperar", example = "9") @PathVariable long id) {
        MovieDto movieDto = this.movieService.getById(id);
        if (movieDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieDto);
    }

    @PostMapping("/suggest")
    public ResponseEntity<String> generateMoviesSuggestion(@RequestBody SuggestRequestDto suggestRequestDto) {
        return ResponseEntity.ok(this.platziPlayAIService.generateMoviesSuggestion(suggestRequestDto.userPreferences()));
    }

    @PostMapping
    public ResponseEntity<MovieDto> add(@RequestBody @Valid MovieDto movieDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.movieService.add(movieDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable long id, @RequestBody @Valid UpdateMovieDto updateMovieDto) {
        return ResponseEntity.ok(this.movieService.update(updateMovieDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieDto> delete(@PathVariable long id) {
        this.movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
