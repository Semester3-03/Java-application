package com.BrewMES.demo.model;

import java.util.List;

public class BrewMES implements iBrewMES {
	private List<Machine> machines;
	private Machine currentMachine;
	private Batch selectedBatch;
	private List<Batch> latestBatches;

	public static void main(String[] args) {
		throw new UnsupportedOperationException();
	}

	public void setCurrentMachine(int machine) {
		throw new UnsupportedOperationException();
	}

	public Batch getBatch(int id) {
		throw new UnsupportedOperationException();
	}

	public void getReport(Batch batch) {
		throw new UnsupportedOperationException();
	}

	public void connectMachine(String ipAddress) {
		throw new UnsupportedOperationException();
	}

	public void disconnectMachine(int id) {
		throw new UnsupportedOperationException();
	}

	public void setMachineVariables(int speed, BeerType beerType, int batchSize) {
		throw new UnsupportedOperationException();
	}

	public void controlMachine(Command command) {
		throw new UnsupportedOperationException();
	}

	public String getMachineVariables() {
		throw new UnsupportedOperationException();
	}

}
