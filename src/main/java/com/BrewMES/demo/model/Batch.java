package com.BrewMES.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;


@Entity
//Specifies target table in the database
@Table(name = "Batch")
public class Batch {

	//specifies that this field is a ID and a primary key.
	@Id
	//Specifies the collumn to map the field value to.
	@Column(name = "id")
	private UUID id;

	@Column(name = "machine_id")
	private UUID machineId;

	@Column(name = "product_type_id")
	private String productType;

	@Column(name = "total_products")
	private int totalProducts;

	@Column(name = "acceptable_products")
	private int acceptableProducts;
	@Column(name = "defect_products")
	private int defectProducts;

	//Specifies a collection of primitive types
	@ElementCollection
	//Target table for the map
	@CollectionTable(name = "time_in_states")
	//Target column for the keys in the map
	@MapKeyColumn(name = "machine_state")
	//Target column for the values in the map
	@Column(name = "time")
	private Map<Integer, Double> timeInStates;

	@ElementCollection
	@CollectionTable(name = "Temperature")
	@MapKeyColumn(name = "time_stamp")
	@Column(name = "temperature")
	private Map<LocalDateTime,Double> temperature;

	@ElementCollection
	@CollectionTable(name = "Vibration")
	@MapKeyColumn(name = "time_stamp")
	@Column(name = "vibration")
	private Map<LocalDateTime,Double> vibration;

	@ElementCollection
	@CollectionTable(name = "Humidity")
	@MapKeyColumn(name = "time_stamp")
	@Column(name = "humidity")
	private Map<LocalDateTime,Double> humidity;

	@Column(name = "min_temp")
	private double minTemp;

	@Column(name = "max_temp")
	private double maxTemp;

	@Transient
	private double avgTemp;

	@Column(name = "min_humidity")
	private double minHumidity;

	@Column(name = "max_humidity")
	private double maxHumidity;

	@Transient
	private double avgHumidity;

	@Column(name = "min_vibration")
	private double minVibration;

	@Column(name = "max_vibration")
	private double maxVibration;

	@Transient
	private double avgVibration;

	public void addTemperature(LocalDateTime time, double temp) {

	}

	public void addVibration(LocalDateTime time, double vibration) {

	}

	public void addHumidity(LocalDateTime time, double humidity) {

	}

	public void setTimeInState(int index, int seconds) {

	}

	private double findAvgTemp(){
		return (double) temperature.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
	}

	private double findAvgHumidity(){
		return (double) humidity.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
	}

	private double findAvgVibration(){
		return (double) vibration.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
	}

	public void setAverages(){
		setAvgHumidity(findAvgHumidity());
		setAvgTemp(findAvgTemp());
		setAvgVibration(findAvgVibration());
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

//	public List<Integer> getTimeInStates() {
//		return timeInStates;
//	}

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

	public double getMinHumidity() {
		return minHumidity;
	}

	public double getMaxHumidity() {
		return maxHumidity;
	}

	public double getAvgHumidity() {
		return avgHumidity;
	}

	public double getMinVibration() {
		return minVibration;
	}

	public double getMaxVibration() {
		return maxVibration;
	}

	public double getAvgVibration() {
		return avgVibration;
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

	public void setMinHumidity(double minHumidity) {
		this.minHumidity = minHumidity;
	}

	public void setMaxHumidity(double maxHumidity) {
		this.maxHumidity = maxHumidity;
	}

	public void setAvgHumidity(double avgHumidity) {
		this.avgHumidity = avgHumidity;
	}

	public void setMinVibration(double minVibration) {
		this.minVibration = minVibration;
	}

	public void setMaxVibration(double maxVibration) {
		this.maxVibration = maxVibration;
	}

	public void setAvgVibration(double avgVibration) {
		this.avgVibration = avgVibration;
	}
}
