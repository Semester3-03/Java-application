package com.brewmes.demo.model;

import javax.persistence.*;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.math.BigDecimal;


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

    @Column(name = "machine_speed")
    private double machineSpeed;

    @Column(name = "product_type_id")
    private int productType;

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

    private double findAvgTemp() {
        return temperature.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private double findAvgHumidity() {
        return humidity.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private double findAvgVibration() {
        return vibration.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public void setAverages() {
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

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
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

//	public List<Integer> getTimeInStates() {
//		return timeInStates;
//	}

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

    public double calculateOee() {
        double goodCount = this.acceptableProducts;
        double idealCycleTime = 1.0 / (double)BeerType.valueOfLabel(this.productType).maxSpeed;
        double plannedProductionTime = 1.0/ (this.machineSpeed / (double)totalProducts);

        BigDecimal bd = new BigDecimal((((goodCount * idealCycleTime) / plannedProductionTime)*100));
        bd = bd.round(new MathContext(5));
        return bd.doubleValue();
    }

    public Batch(int acceptableProducts, int productType, double machineSpeed, int totalProducts){
        this.acceptableProducts = acceptableProducts;
        this.productType = productType;
        this.machineSpeed = machineSpeed;
        this.totalProducts = totalProducts;
    }

    public Batch(){}
}
