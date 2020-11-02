package com.BrewMES.demo.model;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BrewMES implements iBrewMES {
	private Map<Integer, Machine> machines;
	private Machine currentMachine;
	private Batch selectedBatch;
	private List<Batch> latestBatches;

	public static void main(String[] args) {
		throw new UnsupportedOperationException();
	}

	public void setMachines(Map<Integer, Machine> machines) {
		this.machines = machines;
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
		try {
			//get all endpoints from the machine
			List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(ipAddress).get();

			//loading endpoints into configuration
			OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
			cfg.setEndpoint(endpoints.get(0));

			//setting up machine client with config
			OpcUaClient connection = OpcUaClient.create(cfg.build());

			//connecting machine
			connection.connect().get();
			if (machines == null) {
				machines = new HashMap<>();
			}
			Machine newMachine = new Machine(ipAddress, connection);
			machines.put(newMachine.getId(), newMachine);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnectMachine(int id) {
			machines.remove(id);
	}

	public void setMachineVariables(int speed, BeerType beerType, int batchSize) {
		throw new UnsupportedOperationException();
	}
	//Parsing the command to the current selected machine.
	public void controlMachine(Command command) {
		currentMachine.controlMachine(command);
	}

	public String getMachineVariables() {
		throw new UnsupportedOperationException();
	}


	public Map<Integer, Machine> getMachines() {
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

