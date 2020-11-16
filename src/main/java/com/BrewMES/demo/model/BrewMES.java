package com.BrewMES.demo.model;
import com.BrewMES.demo.Persistence.BatchRepository;
import com.BrewMES.demo.Persistence.MachineRepository;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class BrewMES implements iBrewMES {

	//Repository injected by Spring
	@Autowired
	private MachineRepository machineRepo;

	@Autowired
	private BatchRepository batchRepo;

	private Map<UUID, Machine> machines;
	private Machine currentMachine;
	private Batch selectedBatch;
	private List<Batch> latestBatches;


	public void setMachines(Map<UUID, Machine> machines) {
		this.machines = machines;
	}


	// picks based on MachineId
	public void setCurrentMachine(UUID machineId) {
		this.currentMachine = machines.get(machineId);
	}

	public Batch getBatch(UUID id) {
		Batch batch = batchRepo.findById(id).orElse(null);

		if(batch != null){
			batch.setAverages();
		}

		return batch;
	}

	public void getReport(Batch batch) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Connects a machine to our OPC-UA client and saves it in the machines map as well as in the database.
	 * @param ipAddress a String representation of the ip of the machine you wish to connect.
	 */
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
			machineRepo.save(newMachine);
			machines.put(newMachine.getId(), newMachine);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} catch (UaException | ExecutionException  e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes and deletes from both the machine map and the database.
	 * @param id the UUID of the machine you wish to delete.
	 */
	public void disconnectMachine(UUID id) {
			machines.remove(id);
			machineRepo.deleteById(id);
	}

	public void setMachineVariables(int speed, BeerType beerType, int batchSize) {
		this.currentMachine.setVariables(speed, beerType, batchSize);
	}

	//Parsing the command to the current selected machine.
	public void controlMachine(Command command) {
		currentMachine.controlMachine(command);
	}

	public String getMachineVariables() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Finds all machines in the database and updates the Map machines.
	 * @return the updated map of machines.
	 */
	@Override
	public Map<UUID, Machine> getMachines() {
		if (machines == null) {
			machines = new HashMap<>();
		}
		machineRepo.findAll().forEach(machine -> {
			if (!machines.containsKey(machine.getId())) {
				machines.put(machine.getId(), machine);
			}
		});
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
