package com.brewmes.demo.model;

import java.util.Map;
import java.util.UUID;

public interface iBrewMES {

    void controlMachine(Command command);

    void setMachineVariables(int speed, BeerType beerType, int batchSize);

    Machine getCurrentMachine();

    void setCurrentMachine(UUID machine);

    Batch getBatch(int id);

    void getReport(Batch batch);

    void disconnectMachine(UUID id);

    void connectMachine(String ipAddress);

    Map<UUID, Machine> getMachines();

    String getMachineVariables();

}
