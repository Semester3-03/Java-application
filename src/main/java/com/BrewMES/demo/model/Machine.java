package com.BrewMES.demo.model;

public class Machine {
    private int id;
    private String ip;
    private Batch currentBatch;
    private double oee;
    private int currentState;
    private int totalProducts;
    private int acceptableProducts;
    private int defectProducts;
    private double temperature;
    private double vibration;
    private double humidity;

    public Machine(String ipAddress) {
        this.ip = ipAddress;
    }

    public void controlMachine(Command command) {
        throw new UnsupportedOperationException();
    }

    public int readState() {
        throw new UnsupportedOperationException();
    }

    public double READALLTHESTUFFSS() {
        throw new UnsupportedOperationException();
        //TODO: THIS NEEDS TO BE ALOT OF METHODS THAT GETS STUFF FROM THE MACHINE
    }

    private void saveBatch() {
        throw new UnsupportedOperationException();
    }

    private double calculateOEE() {
        throw new UnsupportedOperationException();
    }

    public void setVariables(int speed, BeerType beerType, int batchSize) {
        throw new UnsupportedOperationException();
    }

    public String makeJsonVariables() {
        throw new UnsupportedOperationException();
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public Batch getCurrentBatch() {
        return currentBatch;
    }

    public double getOee() {
        return oee;
    }

    public int getCurrentState() {
        return currentState;
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

    public double getTemperature() {
        return temperature;
    }

    public double getVibration() {
        return vibration;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setCurrentBatch(Batch currentBatch) {
        this.currentBatch = currentBatch;
    }

    public void setOee(double oee) {
        this.oee = oee;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
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

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setVibration(double vibration) {
        this.vibration = vibration;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
