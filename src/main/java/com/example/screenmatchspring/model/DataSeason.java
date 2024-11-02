package com.example.screenmatchspring.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeason(
        @JsonAlias("Season") Integer numberSeason,
        @JsonAlias("Episodes") List<DataEpisode> episodes
) {
}
