package com.brewmes.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
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
    private Map<LocalDateTime, Double> temperature;

    @ElementCollection
    @CollectionTable(name = "Vibration")
    @MapKeyColumn(name = "time_stamp")
    @Column(name = "vibration")
    private Map<LocalDateTime, Double> vibration;

    @ElementCollection
    @CollectionTable(name = "Humidity")
    @MapKeyColumn(name = "time_stamp")
    @Column(name = "humidity")
    private Map<LocalDateTime, Double> humidity;

    @Transient
    private double minTemp;

    @Transient
    private double maxTemp;

    @Transient
    private double avgTemp;

    @Transient
    private double minHumidity;

    @Transient
    private double maxHumidity;

    @Transient
    private double avgHumidity;

    @Transient
    private double minVibration;

    @Transient
    private double maxVibration;

    @Transient
    private double avgVibration;

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

    public void setTimeInState(int state, double seconds) {
      		if (timeInStates == null) {
			timeInStates = new TreeMap<>();
		}
		//save current logged time for state and add new time.
		double time = timeInStates.get(state) + seconds;

		//add new updated entry to list, at the index provided.
		timeInStates.put(state, time);
    }

    private double findAvgTemp() {
        return temperature.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private double findAvgHumidity() {
        return humidity.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private double findAvgVibration() {
        return vibration.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
    private double findMaxTemp() {
        return temperature.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
    }
    private double findMaxVibration() {
        return vibration.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
    }
    private double findMaxHumidity() {
        return humidity.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
    }
    private double findMinTemp() {
        return temperature.values().stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
    }
    private double findMinVibration() {
        return vibration.values().stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
    }
    private double findMinHumidity() {
        return humidity.values().stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
    }

    public void setAverages() {
        setAvgHumidity(findAvgHumidity());
        setAvgTemp(findAvgTemp());
        setAvgVibration(findAvgVibration());
    }
    public void setMaxes() {
        setMaxTemp(findMaxTemp());
        setMaxVibration(findMaxVibration());
        setMaxHumidity(findMaxHumidity());
    }

    public void setMinimums(){
        setMinTemp(findMinTemp());
        setMinVibration(findMinVibration());
        setMinHumidity(findMinHumidity());
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

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getAcceptableProducts() {
        return acceptableProducts;
    }

    public void setAcceptableProducts(int acceptableProducts) {
        this.acceptableProducts = acceptableProducts;
    }

    public int getDefectProducts() {
        return defectProducts;
    }

    public void setDefectProducts(int defectProducts) {
        this.defectProducts = defectProducts;
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

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public double getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(double avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    public double getMinVibration() {
        return minVibration;
    }

    public void setMinVibration(double minVibration) {
        this.minVibration = minVibration;
    }

    public double getMaxVibration() {
        return maxVibration;
    }

    public void setMaxVibration(double maxVibration) {
        this.maxVibration = maxVibration;
    }

    public double getAvgVibration() {
        return avgVibration;
    }

    public void setAvgVibration(double avgVibration) {
        this.avgVibration = avgVibration;
    }
}
