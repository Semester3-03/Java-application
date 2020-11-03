package com.BrewMES.demo.model;

import java.util.Map;
import java.util.UUID;

public interface iBrewMES {

	public abstract void controlMachine(Command command);

	public abstract void setMachineVariables(int speed, BeerType beerType, int batchSize);

	public void setCurrentMachine(UUID machine);

	public Batch getBatch(int id);

	public void getReport(Batch batch);

	public void disconnectMachine(UUID id);

	public void connectMachine(String ipAddress);

	public Map<UUID, Machine> getMachines();

	public abstract String getMachineVariables();

}
