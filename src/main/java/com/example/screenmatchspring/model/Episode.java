package com.example.screenmatchspring.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer season;
    @Column(unique = true)
    private String title;
    private Integer numberEpisode;
    private Double evaluation;
    private LocalDate released;
    @ManyToOne
    private Serie serie;

    public Episode() {}

    public Episode(Integer season, DataEpisode dataEpisode) {
        this.season = season;
        this.title = dataEpisode.title();
        this.numberEpisode = dataEpisode.numberEpisode();
        try {
            this.evaluation = Double.valueOf(dataEpisode.evaluation());
        } catch (NumberFormatException e) {
            this.evaluation = 0.0;
        }

        try {
            this.released = LocalDate.parse(dataEpisode.released());
        } catch (DateTimeParseException e) {
            this.released = null;
        }
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberEpisode() {
        return numberEpisode;
    }

    public void setNumberEpisode(Integer numberEpisode) {
        this.numberEpisode = numberEpisode;
    }

    public Double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Double evaluation) {
        this.evaluation = evaluation;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return "Episode {" +
                "season=" + season +
                ", title='" + title + '\'' +
                ", numberEpisode=" + numberEpisode +
                ", evaluation=" + evaluation +
                ", released=" + released +
                '}';
    }
}
