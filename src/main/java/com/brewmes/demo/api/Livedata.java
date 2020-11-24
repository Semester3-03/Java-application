package com.brewmes.demo.api;

import com.brewmes.demo.model.Machine;

import java.util.UUID;

public class Livedata {

    private double temperature;
    private double vibration;
    private int state;

    public Livedata(Machine machine) {
        this.state = machine.readState();
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getVibration() {
        return vibration;
    }

    public void setVibration(double vibration) {
        this.vibration = vibration;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
