package com.BrewMES.demo;

import com.BrewMES.demo.model.BrewMES;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrewMesApplication {

	//This class was created by Spring, so for now I just let it be -Teis

	public static void main(String[] args) {
		SpringApplication.run(BrewMesApplication.class, args);
		/*
		BrewMES sys = new BrewMES();
		sys.connectMachine("opc.tcp://127.0.0.1:4840");
		sys.connectMachine("opc.tcp://127.0.0.1:4840");
		sys.connectMachine("opc.tcp://127.0.0.1:4840");
		sys.connectMachine("opc.tcp://127.0.0.1:4840");
*/
	}

}
