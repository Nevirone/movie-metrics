package com.example.moviemetrics.api.request;

import com.example.moviemetrics.api.model.Genre;
import com.example.moviemetrics.api.model.Movie;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public class MovieRequest {
    @NotNull(message = "Title is required")
    @Min(value = 5, message = "At least 5 characters")
    private String title;
    @NotNull(message = "Description is required")
    @Min(value =10, message = "At least 10 characters")
    private String description;
    @NotNull(message = "Popularity is required")
    private double popularity;
    @NotNull(message = "VoteAverage is required")
    private double voteAverage;

    @NotNull(message = "VoteCount is required")
    private int voteCount;

    @NotNull(message = "GenreIds is required")
    private Set<Long> genreIds;

    public Movie getMovie() {
        return new Movie(
                this.title,
                this.description,
                this.popularity,
                this.voteAverage,
                this.voteCount
        );
    }
}
