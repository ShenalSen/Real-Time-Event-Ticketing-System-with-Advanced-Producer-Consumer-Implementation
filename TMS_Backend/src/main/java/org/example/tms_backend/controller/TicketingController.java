package org.example.tms_backend.controller;
import org.example.tms_backend.model.Configuration;
import org.example.tms_backend.service.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticketing")
@CrossOrigin(origins = "*") // Allow communication with the frontend
public class TicketingController {

    @Autowired
    private TicketingService ticketingService;
    private Long id;

    /**
     * Postmapping for configuration endpoint
     */
    @PostMapping("/configure")
    public Configuration configureSystem(@RequestBody Configuration configuration) {
        Configuration configuration1 =  ticketingService.saveConfiguration(configuration);
        id = configuration1.getId();
        return configuration1;
    }

    /**
     * Postmapping for start system
     */
    @PostMapping("/start")
    public String startSystem() {
        Configuration configuration = ticketingService.getConfigurationByid(id);
        ticketingService.startSystem(configuration);
        return "System started!";
    }

    /**
     *Postmapping for stop system
     */
    @PostMapping("/stop")
    public String stopSystem() {
        ticketingService.stopSystem();
        return "System stopped!";
    }

    /**
     * Postmapping for reset total ticket count
     */
    @PostMapping("/reset")
    public String resetSystem() {
        ticketingService.resetSystem();
        return "System reset!";
    }

    /**
     *Getmapping for get total ticket count
     */
    @GetMapping("/status")
    public int getTicketCount() {
        return ticketingService.getTicketCount();
    }

    /**
     *Getmapping for get the real time transaction updates
     */
    @GetMapping("/latest-transaction")
    public String getLatestTransaction() {
        return (ticketingService.getLatestTransaction());
    }


}
