package com.brewmes.demo.model;

import com.brewmes.demo.Persistence.BatchRepository;
import com.brewmes.demo.Persistence.MachineRepository;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    private Batch selectedBatch;
    private List<Batch> latestBatches;
    private Thread batchSaveThread;

    public Batch getBatch(UUID id) {
        Batch batch = batchRepo.findById(id).orElse(null);

        if (batch != null) {
            batch.setAverages();
            batch.setMaxes();
            batch.setMinimums();
        }

        return batch;
    }

    public void getReport(Batch batch) {
        throw new UnsupportedOperationException();
    }

    /**
     * Connects a machine to our OPC-UA client and saves it in the machines map as well as in the database.
     *
     * @param ipAddress a String representation of the ip of the machine you wish to connect.
     */
    public boolean connectMachine(String ipAddress) {
        try {
            //get all endpoints from the machine
            OpcUaClient connection = getOpcUaClient(ipAddress);
            if (machines == null) {
                machines = new HashMap<>();
            }
            Machine newMachine = new Machine(ipAddress, connection);
            machineRepo.save(newMachine);
            machines.put(newMachine.getId(), newMachine);
            makeBatchSaveThread();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (UaException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean connectMachineWithID(String ipAddress, UUID id) {
        try {
            OpcUaClient connection = getOpcUaClient(ipAddress);
            if (machines == null) {
                machines = new HashMap<>();
            }
            Machine newMachine = new Machine(ipAddress, connection, id);
            machineRepo.save(newMachine);
            machines.put(newMachine.getId(), newMachine);
            makeBatchSaveThread();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (UaException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    private OpcUaClient getOpcUaClient(String ipAddress) throws InterruptedException, ExecutionException, UaException {
        //get all endpoints from the machine
        List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(ipAddress).get();

        //loading endpoints into configuration
        OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
        cfg.setEndpoint(endpoints.get(0));

        //setting up machine client with config
        OpcUaClient connection = OpcUaClient.create(cfg.build());

        //connecting machine
        connection.connect().get();
        return connection;
    }

    /**
     * Removes and deletes from both the machine map and the database.
     *
     * @param id the UUID of the machine you wish to delete.
     */
    public void disconnectMachine(UUID id) {
        machines.remove(id);
        machineRepo.deleteById(id);
    }

    /**
     * Makes a single thread that loops through all the machines and saves their batch to the database if the state of the machine is 17
     */
    private void makeBatchSaveThread() {
        if(batchSaveThread == null) {

            batchSaveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        for (Machine machine : machines.values()) {
                            if (machine.getCurrentState() == 17) {
                                if (machine.getCurrentBatch() != null) {
                                    if (!machine.getCurrentBatch().isSaved()) {
                                        machine.setCurrentBatch(batchRepo.save(machine.getCurrentBatch()));
                                        machine.getCurrentBatch().setSaved(true);
                                    }
                                }

                            }
                        }

                        try {
                            Thread.currentThread().sleep(5000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            batchSaveThread = null;
                            e.printStackTrace();
                        }
                    }
                }
            });

            batchSaveThread.setDaemon(true);
            batchSaveThread.start();
        }
    }

    public void setMachineVariables(int speed, BeerType beerType, int batchSize, UUID id) {
        machines.get(id).setVariables(speed, beerType, batchSize);
    }

    //Parsing the command to the current selected machine.
    public void controlMachine(Command command, UUID id) {
        machines.get(id).controlMachine(command);
    }

    public String getMachineVariables() {
        throw new UnsupportedOperationException();
    }

    /**
     * Finds all machines in the database and updates the Map machines.
     *
     * @return the updated map of machines.
     */
    @Override
    public Map<UUID, Machine> getMachines() {
        if (machines == null) {
            machines = new HashMap<>();
        }
        machineRepo.findAll().forEach(machine -> {
            if (!machines.containsKey(machine.getId())) {
                connectMachineWithID(machine.getIp(), machine.getId());
            }
        });

        return machines;
    }

    public void setMachines(Map<UUID, Machine> machines) {
        this.machines = machines;
    }

    public Batch getSelectedBatch() {
        return selectedBatch;
    }

    public void setSelectedBatch(Batch selectedBatch) {
        this.selectedBatch = selectedBatch;
    }

    public List<Batch> getLatestBatches() {
        return latestBatches;
    }

    public void setLatestBatches(List<Batch> latestBatches) {
        this.latestBatches = latestBatches;
    }

}
