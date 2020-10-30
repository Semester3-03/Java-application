package com.BrewMES.demo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrewMES implements iBrewMES {
	private List<Machine> machines;
	private Machine currentMachine;
	private Batch selectedBatch;
	private List<Batch> latestBatches;

	public static void main(String[] args) {
		throw new UnsupportedOperationException();
	}

	public void setMachines(Map<Integer, Machine> machines) {
		this.machines = machines;
	}

	public Machine getCurrentMachine() {
		return currentMachine;
	}

	// picks based on MachineId
	public void setCurrentMachine(int machineId) {
		this.currentMachine = machines.get(machineId);
	}

	public Batch getBatch(int id) {
		throw new UnsupportedOperationException();
	}

	public void getReport(Batch batch) {
		throw new UnsupportedOperationException();
	}

	public void connectMachine(String ipAddress) {
		if (machines.size() == 0) {
			machines = new HashMap<>();
		}
		Machine newMachine = new Machine(ipAddress);
		machines.put(newMachine.getId, newMachine);
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


	public List<Machine> getMachines() {
		return machines;
	}

	public Machine getCurrentMachine() {
		return currentMachine;
	}

	public Batch getSelectedBatch() {
		return selectedBatch;
	}

	public List<Batch> getLatestBatches() {
		return latestBatches;
	}

	public void setSelectedBatch(Batch selectedBatch) {
		this.selectedBatch = selectedBatch;
	}

	public void setLatestBatches(List<Batch> latestBatches) {
		this.latestBatches = latestBatches;
	}
}

