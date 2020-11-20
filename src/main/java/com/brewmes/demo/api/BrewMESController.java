package com.brewmes.demo.api;

import com.brewmes.demo.model.BeerType;
import com.brewmes.demo.model.Command;
import com.brewmes.demo.model.iBrewMES;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class BrewMESController {

    //allow spring to inject bean to field
    @Autowired
    private iBrewMES brewMes;

    //Get all machines
    @GetMapping(value = "/machines")
    public ResponseEntity<Object> getMachines() {
        return new ResponseEntity<>(brewMes.getMachines().values().toArray(), HttpStatus.OK);
    }

    //Get a specific machine based on it's id.
    @GetMapping(value = "/machines/{id}")
    public ResponseEntity<Object> getMachine(@PathVariable("id") UUID id) {
        if (brewMes.getMachines().containsKey(id)) {
            return new ResponseEntity<>(brewMes.getMachines().get(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new StringResponse("Sorry i do not know that machine :-)", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    //Set a machine as the currently selected machine
    @PutMapping(value = "/currentmachine")
    public ResponseEntity<Object> setCurrentMachine(@RequestBody String input) {
        JsonObject o = JsonParser.parseString(input).getAsJsonObject();
        String s = o.get("id").getAsString();
        UUID id = UUID.fromString(s);
        brewMes.setCurrentMachine(id);
        return new ResponseEntity<>(new StringResponse("Machine is set as current machine", HttpStatus.OK.value()), HttpStatus.OK);
    }

    //Get the current machine
    @GetMapping(value = "/currentmachine")
    public ResponseEntity<Object> getCurrentMachine() {
        return new ResponseEntity<>(brewMes.getCurrentMachine(), HttpStatus.OK);
    }

    //Delete and disconnect a machine from the system by specifying it's id.
    @DeleteMapping(value = "/machines/{id}")
    public ResponseEntity<Object> deleteMachine(@PathVariable("id") UUID id) {
        brewMes.disconnectMachine(id);
        return new ResponseEntity<>(new StringResponse("Machine is removed", HttpStatus.OK.value()), HttpStatus.OK);
    }

    // Sends a command to the machine. The command is send via put request with a json object that is notated with e.g.
    // {'command': 'start'}
    @PutMapping(value = "machines/{id}/command")
    public ResponseEntity<Object> controlMachine(@PathVariable("id") UUID id, @RequestBody String input) {
        JsonObject o = JsonParser.parseString(input).getAsJsonObject();
        String s = o.get("command").getAsString();
        ResponseEntity<Object> response = new ResponseEntity<>(new StringResponse("command updated", HttpStatus.OK.value()), HttpStatus.OK);
        switch (s) {
            case "start" -> brewMes.getMachines().get(id).controlMachine(Command.START);
            case "stop" -> brewMes.getMachines().get(id).controlMachine(Command.STOP);
            case "reset" -> brewMes.getMachines().get(id).controlMachine(Command.RESET);
            case "abort" -> brewMes.getMachines().get(id).controlMachine(Command.ABORT);
            case "clear" -> brewMes.getMachines().get(id).controlMachine(Command.CLEAR);
            default -> response = new ResponseEntity<>(new StringResponse("I did not understand that.", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    //Connect machine
    //handles post request with json: {"ip":"<ip to connect>"}
    @PostMapping(value = "/machines")
    public ResponseEntity<Object> AddMachine(@RequestBody String input) {
        JsonObject o = JsonParser.parseString(input).getAsJsonObject();
        String ip = o.get("ip").getAsString();
        boolean success = brewMes.connectMachine(ip);

        if(success){
            return new ResponseEntity<>(new StringResponse("Success", HttpStatus.OK.value()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new StringResponse("Failed, not a valid ip", HttpStatus.NOT_ACCEPTABLE.value()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //set machine variables
    @PutMapping(value = "/machines/{id}/variables")
    public ResponseEntity<Object> setMachineVariables(@PathVariable("id") UUID id, @RequestBody String input) {
        JsonObject o = JsonParser.parseString(input).getAsJsonObject();
        int speed = o.get("speed").getAsInt();
        String beerType = o.get("beerType").getAsString();
        int batchSize = o.get("batchSize").getAsInt();
        ResponseEntity<Object> response = new ResponseEntity<>(new StringResponse("Variables set.", HttpStatus.OK.value()), HttpStatus.OK);
        brewMes.setCurrentMachine(id);
        switch (beerType) {
            case "pilsner" -> brewMes.setMachineVariables(speed, BeerType.PILSNER, batchSize);
            case "wheat" -> brewMes.setMachineVariables(speed, BeerType.WHEAT, batchSize);
            case "stout" -> brewMes.setMachineVariables(speed, BeerType.STOUT, batchSize);
            case "ipa" -> brewMes.setMachineVariables(speed, BeerType.IPA, batchSize);
            case "ale" -> brewMes.setMachineVariables(speed, BeerType.ALE, batchSize);
            case "alcohol_free" -> brewMes.setMachineVariables(speed, BeerType.ALCHOL_FREE, batchSize);
            default -> new ResponseEntity<>(new StringResponse("I do not know that beer typer.", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }

        return response;
    }

    //make this method return last 10 batches
    @GetMapping(value = "/batches")
    public ResponseEntity<Object> getBatches() {
        return new ResponseEntity<>(new StringResponse("Not Implemented yet", HttpStatus.NOT_IMPLEMENTED.value()), HttpStatus.NOT_IMPLEMENTED);
    }

    //make this method return a batch based on it's id
    @GetMapping(value = "/batches/{id}")
    public ResponseEntity<Object> getBatch(@PathVariable("id") UUID id) {

        if(brewMes.getBatch(id) != null){
            return new ResponseEntity<>(brewMes.getBatch(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new StringResponse("No batch found with that ID", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/machines/{id}/livedata")
    public ResponseEntity<Object> getLiveData(@PathVariable("id") UUID id) {
        brewMes.setCurrentMachine(id);
        if (brewMes.getLiveData(brewMes.getCurrentMachine()) != null) {
            return new ResponseEntity<>(brewMes.getLiveData(brewMes.getCurrentMachine()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new StringResponse("No data found for that machine", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    //make this method handle the make report and return and return json with it's location
    @GetMapping(value = "/bathes/{id}/generate")
    public ResponseEntity<Object> makeBatchReport(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(new StringResponse("Not Implemented yet", HttpStatus.NOT_IMPLEMENTED.value()), HttpStatus.NOT_IMPLEMENTED);
    }
}
