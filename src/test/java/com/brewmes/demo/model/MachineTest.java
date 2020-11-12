package com.brewmes.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MachineTest {

    public Machine machine;

    @BeforeEach
    void setUp() {
        BrewMES brewMES = BrewMES.getInstance();
        brewMES.connectMachine("opc.tcp://127.0.0.1:4840");
        Machine[] machines = brewMES.getMachines().values().toArray(Machine[]::new);
        brewMES.setCurrentMachine(machines[0].getId());
        machine = brewMES.getMachines().get(machines[0].getId());
    }

    //Only run when the machine is is not connected
    /*
    @Test
    void read_process_count_when_machine_not_connected() {
        assertEquals(-1, machine.readProcessedCount());
    }
    */

    @Test
    void readProcessCount() {
        assertTrue(machine.readProcessedCount() >= 0);
    }

    @Test
    void readDefectiveCountOnSoftwareSimulation() {
        assertEquals(0, machine.readDefectiveCount());
    }

    @Test
    void readStopReason() {
        Integer[] array = {0, 10, 11, 12, 13, 14};
        List<Integer> validValues = Arrays.asList(array);
        assertTrue(validValues.contains(machine.readStopReason()));
    }

    @Test
    void readBatchBeerType() {
        Integer[] array = {0, 1, 2, 3, 4, 5};
        List<Integer> validValues = Arrays.asList(array);
        assertTrue(validValues.contains(machine.readBatchBeerType()));
    }

    @Test
    void readStateOfMachine() {
        Integer[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 13, 14, 15, 16, 17, 18, 19};
        List<Integer> validValues = Arrays.asList(array);
        assertTrue(validValues.contains(machine.readState()));
    }

    @Test
    void readMachineSpeed() {
        int productType = machine.readBatchBeerType();
        int machineSpeed = machine.readMachineSpeed();

        switch (productType) {
            case 0 -> assertTrue(machineSpeed >= 0 && machineSpeed <= 600);
            case 1 -> assertTrue(machineSpeed >= 0 && machineSpeed <= 300);
            case 2 -> assertTrue(machineSpeed >= 0 && machineSpeed <= 150);
            case 3 -> assertTrue(machineSpeed >= 0 && machineSpeed <= 200);
            case 4 -> assertTrue(machineSpeed >= 0 && machineSpeed <= 100);
            case 5 -> assertTrue(machineSpeed >= 0 && machineSpeed <= 125);
            default -> fail();
        }
    }

    @Test
    void readNormalizedMachineSpeed() {
        double machineSpeed = machine.readNormalizedMachineSpeed();
        assertTrue(machineSpeed >= 0.0 && machineSpeed <= 100.0);
    }

    @Test
    void readIdOfCurrentBatch() {
        int id = machine.readBatchCurrentId();
        assertTrue(id >= 0 && id <= 65535);
    }

    @Test
    void readbatchSize() {
        assertTrue(machine.readBatchSize() >= 0);
    }

    @Test
    void readRelativeHumidity() {
        assertTrue(machine.readHumidity() >= 0.0);
    }

    @Test
    void readTemperature() {
        double temp = machine.readTemperature();
        assertTrue(temp >= 0.0 && temp <= 100.0);
    }

    @Test
    void readVibration() {
        double vibration = machine.readVibration();
        assertTrue(vibration >= -50.0 && vibration <= 50.0);
    }

    @Test
    void setVariables() {
        int speed = 600;
        BeerType beerType = BeerType.PILSNER;
        int batchSize = 100;

        machine.setVariables(speed, beerType, batchSize);
        assertEquals(speed, machine.readMachineSpeed());
        assertEquals(beerType.label, machine.readBatchBeerType());
        assertEquals(batchSize, machine.readBatchSize());
    }

}
