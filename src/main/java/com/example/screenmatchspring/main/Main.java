package com.example.screenmatchspring.main;

import com.example.screenmatchspring.model.*;
import com.example.screenmatchspring.repository.SerieRepository;
import com.example.screenmatchspring.service.ConsumeAPI;
import com.example.screenmatchspring.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private ConvertData converter = new ConvertData();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=40ff3a81";
    private List<DataSerie> dataSeries = new ArrayList<>();
    private SerieRepository repository;
    private List<Serie> series;
    private Optional<Serie> serieFound;

    public Main(SerieRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {

        var option = -1;
        while (option != 0) {
            var menu = """
                    1. Search serie
                    2. Search episodes
                    3. Show searched series
                    4. Search series by title
                    5. Top 5 best series
                    6. Search series by category
                    7. Filter series by seasons and evaluation
                    8. Episodes by name
                    9. Search top 5 episodes
                    0. Exit
                    """;

            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchWebSeries();
                    break;
                case 2:
                    searchEpisodesBySerie();
                    break;
                case 3:
                    showSearchedSeries();
                    break;
                case 4:
                    searchSerieByTitle();
                    break;
                case 5:
                    searchTop5Series();
                    break;
                case 6:
                    searchSeriesByCategory();
                    break;
                case 7:
                    filterSeriesBySeasonsAndEvaluation();
                    break;
                case 8:
                    searchEpisodesByTitle();
                    break;
                case 9:
                    searchTop5Episodes();
                    break;
                case 0:
                    System.out.println("Closing the application...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private DataSerie getDataSerie(){
        System.out.println("Enter the name of the serie: ");
        var nameSerie = scanner.nextLine();
        var json = consumeAPI.getData(URL_BASE + nameSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DataSerie data = converter.getData(json, DataSerie.class);
        return data;
    }

    private void searchEpisodesBySerie(){
        showSearchedSeries();
        System.out.println("Enter the title of the serie: ");
        var nameSerie = scanner.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitle().toUpperCase().equals(nameSerie.toUpperCase()))
                .findFirst();

        if (serie.isPresent()){
            var serieFound = serie.get();
            List<DataSeason> seasons = new ArrayList<>();

            for (int i = 1; i < serieFound.getTotalSeasons(); i++){
                var json = consumeAPI.getData(URL_BASE + serieFound.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                DataSeason dataSeason = converter.getData(json, DataSeason.class);
                seasons.add(dataSeason);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(s -> s.episodes().stream()
                            .map(e -> new Episode(s.numberSeason(), e)))
                    .collect(Collectors.toList());

            serieFound.setEpisodes(episodes);
            repository.save(serieFound);
        }

    }

    private void searchWebSeries() {
        DataSerie data = getDataSerie();
        Serie serie = new Serie(data);
        repository.save(serie);
        //dataSeries.add(data);
        System.out.println(data);
    }

    private void showSearchedSeries() {
        series = repository.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }

    private void searchSerieByTitle() {
        System.out.println("Enter the title of the serie: ");
        var title = scanner.nextLine();
        serieFound = repository.findByTitleContainsIgnoreCase(title);

        if (serieFound.isPresent()){
            System.out.println("Serie found: " + serieFound.get());
        } else {
            System.out.println("Serie not found");
        }
    }

    private void searchTop5Series() {
        List<Serie> top5 = repository.findTop5ByOrderByEvaluationDesc();
        top5.forEach(s -> System.out.println(s.getTitle() + " - " + s.getEvaluation()));
    }

    private void searchSeriesByCategory() {
        System.out.println("Enter the category: ");
        var genre = scanner.nextLine();
        var category = Category.fromEspanol(genre);
        List<Serie> series = repository.findByGenre(category);
        System.out.println("Series of the category " + genre);
        series.forEach(System.out::println);
    }

    private void filterSeriesBySeasonsAndEvaluation() {
        System.out.println("Fitler series by seasons: ");
        int seasons = scanner.nextInt();
        System.out.println("Filter series by evaluation: ");
        double evaluation = scanner.nextDouble();
        List<Serie> series = repository.seriesBySeasonAndEvaluation(seasons, evaluation);
        series.forEach(serie -> System.out.println(serie.getTitle() + " - " + serie.getEvaluation()));
    }

    private void searchEpisodesByTitle() {
        System.out.println("Enter the title of the episode: ");
        var title = scanner.nextLine();
        List<Episode> foundEpisodes = repository.episodesByName(title);
        foundEpisodes.forEach(episode -> System.out.printf("Serie: %s - Temporada: %s - Episode: %s - Evaluation: %s\n",
                episode.getSerie().getTitle(), episode.getSeason(), episode.getTitle(), episode.getEvaluation()));
    }

    private void searchTop5Episodes() {
        searchSerieByTitle();
        if (serieFound.isPresent()){
            List<Episode> top5Episodes = repository.top5Episodes(serieFound.get());
            top5Episodes.forEach(episode -> System.out.printf("Serie: %s - Temporada: %s - Episode: %s - Evaluation: %s\n",
                    episode.getSerie().getTitle(), episode.getSeason(), episode.getTitle(), episode.getEvaluation()));
        }
    }
}
