package com.BrewMES.demo.model;

import java.util.Map;

public interface iBrewMES {

	public abstract void controlMachine(Command command);

	public abstract void setMachineVariables(int speed, BeerType beerType, int batchSize);

	public void setCurrentMachine(int machine);

	public Batch getBatch(int id);

	public void getReport(Batch batch);

	public void disconnectMachine(int id);

	public void connectMachine(String ipAddress);

	public Map<Integer, Machine> getMachines();

	public abstract String getMachineVariables();

}
