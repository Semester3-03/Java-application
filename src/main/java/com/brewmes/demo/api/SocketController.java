package com.brewmes.demo.api;

import com.brewmes.demo.model.Machine;
import com.brewmes.demo.model.iBrewMES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

import java.util.UUID;

@Controller
public class SocketController {

    @Autowired
    private iBrewMES brewMes;

    @MessageMapping("/connect/{id}")
    @SendTo("/topic/{id}/livedata")
    @CrossOrigin(origins = "*")
    public Machine livedata(@DestinationVariable("id") UUID id) throws Exception {

        Machine machine = brewMes.getMachines().get(id);
        machine.readLiveData();

        Thread.sleep(1000); // simulated delay
        return machine;
    }
}
