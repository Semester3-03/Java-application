package com.brewmes.demo;

import com.brewmes.demo.model.Batch;
import com.brewmes.demo.model.Report;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
public class BrewMesApplication {
	//This class was created by Spring, so for now I just let it be -Teis
	public static void main(String[] args) {
		SpringApplication.run(BrewMesApplication.class, args);
	}
}
