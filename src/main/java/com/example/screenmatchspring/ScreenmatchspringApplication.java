package com.example.screenmatchspring;

import com.example.screenmatchspring.main.Main;
import com.example.screenmatchspring.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchspringApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchspringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository);
		main.showMenu();
	}
}
