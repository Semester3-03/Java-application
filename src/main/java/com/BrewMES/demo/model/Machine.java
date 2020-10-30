package com.BrewMES.demo.model;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.util.List;

public class Machine {
    private int id;
    private String ip;
    private OpcUaClient connection;
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
        throw new UnsupportedOperationException();
    }


    //region READ HELPER METHODS
    /**
     * Reads an integer value from the machine via OPCUA
     * @param ns is the namespace
     * @param address is the address eg. "::Program:Cube.Status.StateCurrent"
     * @return the integer value if found and -1 otherwise
     */
    private int readInt(int ns, String address) {
        try {
            NodeId nodeId = new NodeId(ns, address);
            DataValue dataValue = connection.readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            return (int) variant.getValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Reads a double value from the machine via OPCUA
     * @param ns is the namespace
     * @param address is the address eg. "::Program:Cube.Status.StateCurrent"
     * @return the double value if found and -1 otherwise
     */
    private double readDouble(int ns, String address) {
        try {
            NodeId nodeId = new NodeId(ns, address);
            DataValue dataValue = connection.readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            return (float) variant.getValue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
    //endregion

    //region READ ADMIN ADDRESSES
    public int readProcessedCount() {
        // Read value of the processed product count
        // Ns: 6
        // ::Program:Cube.Admin.ProdProcessedCount
        return readInt(6, "::Program:Cube.Admin.ProdProcessedCount");
    }

    public int readDefectiveCount() {
        // Read value of the processed product count
        // Ns: 6
        // ::Program:Cube.Admin.ProdDefectiveCount
        return readInt(6, "::Program:Cube.Admin.ProdDefectiveCount");
    }

    public int readStopReason() {
        // Reads id of the stop reason
        // Ns: 6
        // ::Program:Cube.Admin.StopReason.ID
        return readInt(6, "::Program:Cube.Admin.StopReason.ID");
    }

    public int readBatchBeerType() {
        // Reads beer type of current batch
        // Ns: 6
        // ::Program:Cube.Admin.Parameter[0].Value
        return (int) readDouble(6, "::Program:Cube.Admin.Parameter[0].Value");
    }
    //endregion

    //region READ STATUS ADDRESSES
    public int readState() {
        // Read value of the state of the machine
        // Ns: 6
        // ::Program:Cube.Status.StateCurrent
        return readInt(6, "::Program:Cube.Status.StateCurrent");
    }

    public int readMachineSpeed() {
        // Reads the current machine speed
        // Ns: 6
        // ::Program:Cube.Status.MachSpeed
        return (int) readDouble(6, "::Program:Cube.Status.MachSpeed");
    }

    public double readNormalizedMachineSpeed() {
        // Reads the normalized machine speed
        // Ns: 6
        // ::Program:Cube.Status.CurMachSpeed
        return readDouble(6, "::Program:Cube.Status.CurMachSpeed");
    }

    public int readBatchCurrentId() {
        // Reads the current batch id
        // Ns: 6
        // ::Program:Cube.Status.Parameter[0].Value
        return (int) readDouble(6, "::Program:Cube.Status.Parameter[0].Value");
    }

    public int readBatchSize() {
        // Reads the current batch size
        // Ns: 6
        // ::Program:Cube.Status.Parameter[1].Value
        return (int) readDouble(6, "::Program:Cube.Status.Parameter[1].Value");
    }

    public double readHumidity() {
        // Reads the current humidity
        // Ns:
        // ::Program:Cube.Status.Parameter[2].Value
        return readDouble(6, "::Program:Cube.Status.Parameter[2].Value");
    }

    public double readTemperature() {
        // Reads the current temperature
        // Ns:
        // ::Program:Cube.Status.Parameter[3].Value
        return readDouble(6, "::Program:Cube.Status.Parameter[3].Value");
    }

    public double readVibration() {
        // Reads the current vibration
        // Ns:
        // ::Program:Cube.Status.Parameter[4].Value
        return readDouble(6, "::Program:Cube.Status.Parameter[4].Value");
    }
    //endregion

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
}
