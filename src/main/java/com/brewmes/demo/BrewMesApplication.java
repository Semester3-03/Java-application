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
		Batch batch = new Batch();
		batch.addHumidity(LocalDateTime.now(), 44);
		batch.addHumidity(LocalDateTime.now().plusSeconds(30),33);
		batch.addHumidity(LocalDateTime.now().plusSeconds(45),65);
		batch.addTemperature(LocalDateTime.now(), 10);
		batch.addTemperature(LocalDateTime.now().plusSeconds(10), 10);
		batch.addTemperature(LocalDateTime.now().plusSeconds(15), 15);
		batch.addTemperature(LocalDateTime.now().plusSeconds(20), 5);
		Random rand = new Random();
		for (int i = 0; i < 500; i++) {
			batch.addVibration(LocalDateTime.now().plusSeconds(i+3),rand.nextInt(15));
		}
		batch.setTimeInState(0, 10);
		batch.setTimeInState(1, 14);
		batch.setTimeInState(2, 5);
		batch.setTimeInState(4, 25);
		Report.generatePDF(batch);
	}
}
