package com.example.screenmatchspring.repository;

import com.example.screenmatchspring.model.Category;
import com.example.screenmatchspring.model.Episode;
import com.example.screenmatchspring.model.Serie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTitleContainsIgnoreCase(String nameTitle);
    List<Serie> findTop5ByOrderByEvaluationDesc();
    List<Serie> findByGenre(Category category);
    //List<Serie> findByTotalSeasonsLessThanEqualAndEvaluationGreaterThanEqual(int totalSeasons, double evaluation);

    @Query("SELECT s FROM Serie s WHERE s.totalSeasons <= :totalSeasons AND s.evaluation >= :evaluation")
    List<Serie> seriesBySeasonAndEvaluation(int totalSeasons, double evaluation);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE e.title ILIKE %:name%")
    List<Episode> episodesByName(String name);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie ORDER BY e.evaluation DESC LIMIT 5")
    List<Episode> top5Episodes(Serie serie);
}