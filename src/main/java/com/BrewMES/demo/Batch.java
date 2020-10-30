package com.BrewMES.demo;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Batch {
	private int id;
	private int machineId;
	private String productType;
	private int totalProducts;
	private int acceptableProducts;
	private int defectProducts;
	private List<Integer> timeInStates;
	private Map<LocalDateTime,Double> temperature;
	private Map<LocalDateTime,Double> vibration;
	private Map<LocalDateTime,Double> humidity;
	private double minTemp;
	private double maxTemp;
	private double avgTemp;

	public void addTemperature(LocalDateTime time, double temp) {

	}

	public void addVibration(LocalDateTime time, double vibration) {

	}

	public void addHumidity(LocalDateTime time, double humidity) {

	}

	public void setTimeInState(int index, int seconds) {

	}

}
