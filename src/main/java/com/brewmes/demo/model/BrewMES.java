package com.BrewMES.demo.model;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.UUID;


public class BrewMES implements iBrewMES {
	private Map<UUID, Machine> machines;
	private Machine currentMachine;
	private Batch selectedBatch;
	private List<Batch> latestBatches;
	private static BrewMES instance;

	public static void main(String[] args) {
		throw new UnsupportedOperationException();
	}
	//private constructor avoids others to create instances.
	private BrewMES() {

	}
	//Singleton method to get the BrewMes instance.
	public static BrewMES getInstance() {
		if (instance == null) {
			instance = new BrewMES();
		}
		return instance;
	}

	public void setMachines(Map<UUID, Machine> machines) {
		this.machines = machines;
	}


	// picks based on MachineId
	public void setCurrentMachine(UUID machineId) {
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
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} catch (UaException | ExecutionException  e) {
			e.printStackTrace();
		}
	}

	public void disconnectMachine(UUID id) {
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

	@Override
	public Map<UUID, Machine> getMachines() {
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

