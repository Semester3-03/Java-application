package com.brewmes.demo.model;

import java.util.Map;
import java.util.UUID;

public interface iBrewMES {

    public abstract void controlMachine(Command command, UUID id);

    public abstract void setMachineVariables(int speed, BeerType beerType, int batchSize, UUID id);

	public Batch getBatch(UUID id);

    public void getReport(Batch batch);

    public void disconnectMachine(UUID id);

    public boolean connectMachine(String ipAddress);

    public Map<UUID, Machine> getMachines();

    public abstract String getMachineVariables();

}
