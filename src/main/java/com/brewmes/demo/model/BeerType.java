package com.BrewMES.demo.model;

public enum BeerType {
    PILSNER(0),
    WHEAT(1),
    IPA(2),
    STOUT(3),
    ALE(4),
    ALCHOL_FREE(5);

    public final int label;

    BeerType(int label) {
        this.label = label;
    }
}
