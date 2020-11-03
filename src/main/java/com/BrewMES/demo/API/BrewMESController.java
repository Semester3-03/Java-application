package com.BrewMES.demo.API;

import com.BrewMES.demo.model.BeerType;
import com.BrewMES.demo.model.BrewMES;
import com.BrewMES.demo.model.Command;
import com.BrewMES.demo.model.iBrewMES;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BrewMESController {
    //TODO: make brewMes singleton
    private iBrewMES brewMes = new BrewMES();

    //Get all machines
    @RequestMapping(value = "/machines")
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

    //Delete and disconnect a machine from the system by specifying it's id.
    @DeleteMapping(value = "/machines/{id}")
    public ResponseEntity<Object> deleteMachine(@PathVariable("id") UUID id) {
        brewMes.disconnectMachine(id);
        return new ResponseEntity<>(new StringResponse("Machine is removed", HttpStatus.OK.value()), HttpStatus.OK);
    }

    // Sends a command to the machine. The command is send via put request with a json object that is notated with e.g.
    // {'command': 'start'}
    @PutMapping(value = "machines/{id}/command")
    public ResponseEntity<Object> updateMachineState(@PathVariable("id") UUID id, @RequestBody String input) {
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
        brewMes.connectMachine(ip);
        return new ResponseEntity<>(new StringResponse("Succes", HttpStatus.OK.value()), HttpStatus.OK);
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
        return new ResponseEntity<>(new StringResponse("Not Implemented yet", HttpStatus.NOT_IMPLEMENTED.value()), HttpStatus.NOT_IMPLEMENTED);
    }

    //make this method handle the make report and return and return json with it's location
    @GetMapping(value = "/bathes/{id}/geneate")
    public ResponseEntity<Object> makeBatchReport(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(new StringResponse("Not Implemented yet", HttpStatus.NOT_IMPLEMENTED.value()), HttpStatus.NOT_IMPLEMENTED);
    }
}
