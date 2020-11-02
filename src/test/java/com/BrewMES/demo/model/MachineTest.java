package com.BrewMES.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MachineTest {

    public Machine machine;

    @BeforeEach
    void setUp() {
        BrewMES brewMES = new BrewMES();
        brewMES.connectMachine("opc.tcp://127.0.0.1:4840");
        brewMES.setCurrentMachine(0);
        machine = brewMES.getMachines().get(0);
    }

    //Only run when the machine is is not connected
    /*
    @Test
    void read_process_count_when_machine_not_connected() {
        assertEquals(-1, machine.readProcessedCount());
    }
    */

    @Test
    void read_process_count() {
        assertTrue(machine.readProcessedCount() >= 0);
    }

    @Test
    void read_defective_count_on_software_simulation() {
        assertEquals(0, machine.readDefectiveCount());
    }

    @Test
    void read_stop_reason() {
        Integer[] array = {0, 10, 11, 12, 13, 14};
        List<Integer> validValues = Arrays.asList(array);
        assertTrue(validValues.contains(machine.readStopReason()));
    }

    @Test
    void read_batch_beer_type() {
        Integer[] array = {0, 1, 2, 3, 4, 5};
        List<Integer> validValues = Arrays.asList(array);
        assertTrue(validValues.contains(machine.readBatchBeerType()));
    }

    @Test
    void read_state_of_machine() {
        Integer[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 13, 14, 15, 16, 17, 18, 19};
        List<Integer> validValues = Arrays.asList(array);
        assertTrue(validValues.contains(machine.readState()));
    }

    @Test
    void read_machine_speed() {
        int productType = machine.readBatchBeerType();
        int machineSpeed = machine.readMachineSpeed();

        switch (productType){
            case 0:
                assertTrue(machineSpeed >= 0 && machineSpeed <= 600);
                break;
            case 1:
                assertTrue(machineSpeed >= 0 && machineSpeed <= 300);
                break;
            case 2:
                assertTrue(machineSpeed >= 0 && machineSpeed <= 150);
                break;
            case 3:
                assertTrue(machineSpeed >= 0 && machineSpeed <= 200);
                break;
            case 4:
                assertTrue(machineSpeed >= 0 && machineSpeed <= 100);
                break;
            case 5:
                assertTrue(machineSpeed >= 0 && machineSpeed <= 125);
                break;
            default:
                assertTrue(false);
        }
    }

    @Test
    void read_normalized_machine_speed() {
        double machineSpeed = machine.readNormalizedMachineSpeed();
        assertTrue(machineSpeed >= 0.0 && machineSpeed <= 100.0);
    }

    @Test
    void read_id_of_current_batch() {
        int id = machine.readBatchCurrentId();
        assertTrue(id >= 0 && id <= 65535);
    }

    @Test
    void read_batch_size() {
        assertTrue(machine.readBatchSize() >= 0);
    }

    @Test
    void read_relative_humidity() {
        assertTrue(machine.readHumidity() >= 0.0);
    }

    @Test
    void read_temperature() {
        double temp = machine.readTemperature();
        assertTrue(temp >= 0.0 && temp <= 100.0);
    }

    @Test
    void read_vibration() {
        double vibration = machine.readVibration();
        assertTrue(vibration >= -50.0 && vibration <= 50.0);
    }

}