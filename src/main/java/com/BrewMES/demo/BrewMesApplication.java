package com.BrewMES.demo;

import com.BrewMES.demo.model.Batch;
import com.BrewMES.demo.model.Report;
import org.apache.tomcat.jni.Time;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;


@SpringBootApplication
public class BrewMesApplication {
	//This class was created by Spring, so for now I just let it be -Teis
	public static void main(String[] args) {
		SpringApplication.run(BrewMesApplication.class, args);
//			Report report = new Report();
//			Batch batch = new Batch();
//			batch.addHumidity(LocalDateTime.now(), 44);
//			batch.addHumidity(LocalDateTime.now().plusSeconds(30),33);
//			batch.addHumidity(LocalDateTime.now().plusSeconds(45),65);
//			batch.addTemperature(LocalDateTime.now(), 10);
//			batch.addTemperature(LocalDateTime.now().plusSeconds(10), 10);
//			batch.addTemperature(LocalDateTime.now().plusSeconds(15), 15);
//			batch.addTemperature(LocalDateTime.now().plusSeconds(20), 5);
//			batch.addVibration(LocalDateTime.now(), 5);
//			batch.addVibration(LocalDateTime.now().plusSeconds(10), 10);
//			batch.addVibration(LocalDateTime.now().plusSeconds(55), 15);
//			Report.generatePDF(batch);
	}
}