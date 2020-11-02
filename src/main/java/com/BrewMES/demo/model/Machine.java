package com.BrewMES.demo.model;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

import java.util.concurrent.ExecutionException;

public class Machine {
    private int id;
    private String ip;
    private Batch currentBatch;
    private double oee;
    private int currentState;
    private int totalProducts;
    private int acceptableProducts;
    private int defectProducts;
    private double temperature;
    private double vibration;
    private double humidity;
    private OpcUaClient connection;

    public Machine(String ipAddress,OpcUaClient connection) {
        this.ip = ipAddress;
        this.connection = connection;
    }

    public Machine(String ipAddress) {
        this.ip = ipAddress;
    }

    public void controlMachine(Command command) {
            try {
                //Create nodeID for Control Command.
                NodeId cntrlCmd = new NodeId(6, "::Program:Cube.Command.CntrlCmd");

                // Switch on the enum, writing different values to the machine.
                switch (command) {
                    case RESET -> connection.writeValue(cntrlCmd, DataValue.valueOnly(new Variant(1))).get();
                    case START -> connection.writeValue(cntrlCmd, DataValue.valueOnly(new Variant(2))).get();
                    case STOP -> connection.writeValue(cntrlCmd, DataValue.valueOnly(new Variant(3))).get();
                    case ABORT -> connection.writeValue(cntrlCmd, DataValue.valueOnly(new Variant(4))).get();
                    case CLEAR -> connection.writeValue(cntrlCmd, DataValue.valueOnly(new Variant(5))).get();
                    default -> System.out.println("I did not understand that command :-)");
                }
                //request change
                changeRequest();

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
    }

    public int readState() {
        throw new UnsupportedOperationException();
    }

    public double READALLTHESTUFFSS() {
        throw new UnsupportedOperationException();
        //TODO: THIS NEEDS TO BE ALOT OF METHODS THAT GETS STUFF FROM THE MACHINE
    }

    private void saveBatch() {
        throw new UnsupportedOperationException();
    }

    private double calculateOEE() {
        throw new UnsupportedOperationException();
    }

    public void setVariables(int speed, BeerType beerType, int batchSize) {
        throw new UnsupportedOperationException();
    }

    public String makeJsonVariables() {
        throw new UnsupportedOperationException();
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public Batch getCurrentBatch() {
        return currentBatch;
    }

    public double getOee() {
        return oee;
    }

    public int getCurrentState() {
        return currentState;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public int getAcceptableProducts() {
        return acceptableProducts;
    }

    public int getDefectProducts() {
        return defectProducts;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getVibration() {
        return vibration;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setCurrentBatch(Batch currentBatch) {
        this.currentBatch = currentBatch;
    }

    public void setOee(double oee) {
        this.oee = oee;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public void setAcceptableProducts(int acceptableProducts) {
        this.acceptableProducts = acceptableProducts;
    }

    public void setDefectProducts(int defectProducts) {
        this.defectProducts = defectProducts;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setVibration(double vibration) {
        this.vibration = vibration;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    private void changeRequest(){
        try {
            //Create NodeID for Command Change Request.
            NodeId cmdChangeRequest = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");

            //write true to the machine, to pass the case from the switch statement.
            connection.writeValue(cmdChangeRequest, DataValue.valueOnly(new Variant(true))).get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
