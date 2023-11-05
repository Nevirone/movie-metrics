package com.example.moviemetrics.api.controller;
import com.example.moviemetrics.api.exception.GenreNameTakenException;
import com.example.moviemetrics.api.exception.GenreNotFoundException;
import com.example.moviemetrics.api.model.Genre;
import com.example.moviemetrics.api.request.MovieRequest;
import com.example.moviemetrics.api.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.moviemetrics.api.exception.MovieNotFoundException;
import com.example.moviemetrics.api.exception.MovieTitleTakenException;

import com.example.moviemetrics.api.model.Movie;

import com.example.moviemetrics.api.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final GenreService genreService;

    @Autowired
    public MovieController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody MovieRequest movieRequest) {
        Movie newMovie = movieRequest.getMovie();
        newMovie.setGenresByIds(movieRequest.getGenreIds(), genreService);

        try {
            Movie createdMovie = movieService.createMovie(newMovie);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
        } catch (MovieTitleTakenException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        try {
            Movie movie = movieService.getMovieById(id);
            return ResponseEntity.status(HttpStatus.OK).body(movie);
        } catch (MovieNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();

        return ResponseEntity.status(HttpStatus.OK).body(movies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovies(@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
        Movie newMovie = movieRequest.getMovie();
        newMovie.setId(id);
        newMovie.setGenresByIds(movieRequest.getGenreIds(), genreService);

        try {
            Movie updatedMovie = movieService.updateMovie(id, newMovie);
            return ResponseEntity.status(HttpStatus.OK).body(updatedMovie);
        } catch (MovieNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (MovieTitleTakenException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        try {
            Movie deletedMovie = movieService.deleteMovie(id);
            return ResponseEntity.status(HttpStatus.OK).body(deletedMovie);
        } catch (MovieNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}