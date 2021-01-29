package com.brewmes.demo.model;

public class Production {

    private BeerType type;
    private int speed;
    private int amount;

    public Production(BeerType type, int speed, int amount) {
        this.type = type;
        this.speed = speed;
        this.amount = amount;
    }

    public Production() {
    }

    public BeerType getType() {
        return type;
    }

    public void setType(BeerType type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
