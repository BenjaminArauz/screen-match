package com.example.screenmatchspring.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String title;
    private Integer totalSeasons;
    private Double evaluation;
    @Enumerated(EnumType.STRING)
    private Category genre;
    private String plot;
    private String poster;
    private String actors;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodes;

    public Serie(){}

    public Serie(DataSerie dataSerie){
        this.title = dataSerie.title();
        this.totalSeasons = dataSerie.totalSeasons();
        this.evaluation = OptionalDouble.of(Double.valueOf(dataSerie.evaluation())).orElse(0.0);
        this.genre = Category.fromString(dataSerie.genre().split(",")[0].trim());
        this.plot = dataSerie.plot();
        this.poster = dataSerie.poster();
        this.actors = dataSerie.actors();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Double evaluation) {
        this.evaluation = evaluation;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        episodes.forEach(e -> e.setSerie(this));
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return "Serie {" +
                "title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", evaluation=" + evaluation +
                ", genre=" + genre +
                ", plot='" + plot + '\'' +
                ", poster='" + poster + '\'' +
                ", actors='" + actors + '\'' +
                ", episodes='" + episodes + '\'' +
                '}';
    }
}
