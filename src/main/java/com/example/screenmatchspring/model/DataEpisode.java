package com.example.screenmatchspring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisode(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer numberEpisode,
        @JsonAlias("imdbRating") String evaluation,
        @JsonAlias("Released") String released

) {
}
