package com.example.screenmatchspring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSerie(
        @JsonAlias("Title") String title,
        @JsonAlias("totalSeasons") Integer totalSeasons,
        @JsonAlias("imdbRating") String evaluation,
        @JsonAlias("Genre") String genre,
        @JsonAlias("Plot") String plot,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Actors") String actors
) {

}
