package com.brewmes.demo.model;

import java.time.LocalDateTime;
import java.util.*;

public class Batch {
	private UUID id;
	private UUID machineId;
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

	public Batch(){
		this.id = UUID.randomUUID();
	}

	public void addTemperature(LocalDateTime time, double temp) {
		if (temperature == null){
			temperature = new TreeMap<>();
		}
		temperature.put(time, temp);
	}

	public void addVibration(LocalDateTime time, double vibration) {
		if (this.vibration == null){
			this.vibration = new TreeMap<>();
		}
		this.vibration.put(time, vibration);
	}

	public void addHumidity(LocalDateTime time, double humidity) {
		if (this.humidity == null) {
			this.humidity = new TreeMap<>();
		}
		this.humidity.put(time , humidity);
	}

	public void setTimeInState(int index, int seconds) {
		if (timeInStates == null) {
			timeInStates = new ArrayList<>();
			for (int i = 0; i < 17; i++) {
				timeInStates.add(i, 0);
			}
		}
		//save current logged time for state and add new time.
		int time = timeInStates.get(index) + seconds;
		//remove entry from list
		timeInStates.remove(index);
		//add new updated entry to list, at the index provided.
		timeInStates.add(index, time);
	}

	public UUID getId() {
		return id;
	}

	public UUID getMachineId() {
		return machineId;
	}

	public String getProductType() {
		return productType;
	}

	public int getTotalProducts() {
		return totalProducts;
	}

	public int getAcceptableProducts() {
		return acceptableProducts;
	}

	public int getDefectProducts() {
		return defectProducts;
	}

	public List<Integer> getTimeInStates() {
		return timeInStates;
	}

	public Map<LocalDateTime, Double> getTemperature() {
		return temperature;
	}

	public Map<LocalDateTime, Double> getVibration() {
		return vibration;
	}

	public Map<LocalDateTime, Double> getHumidity() {
		return humidity;
	}

	public double getMinTemp() {
		return minTemp;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public double getAvgTemp() {
		return avgTemp;
	}

	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}

	public void setAcceptableProducts(int acceptableProducts) {
		this.acceptableProducts = acceptableProducts;
	}

	public void setDefectProducts(int defectProducts) {
		this.defectProducts = defectProducts;
	}

	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public void setAvgTemp(double avgTemp) {
		this.avgTemp = avgTemp;
	}
}
