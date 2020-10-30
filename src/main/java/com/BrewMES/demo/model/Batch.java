package com.BrewMES.demo.model;

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

	public int getId() {
		return id;
	}

	public int getMachineId() {
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
