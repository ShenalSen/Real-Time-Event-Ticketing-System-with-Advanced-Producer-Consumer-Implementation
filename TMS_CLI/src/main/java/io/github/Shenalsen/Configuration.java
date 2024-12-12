package io.github.Shenalsen;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public record Configuration(int totalNumberOfTickets,
                            int ticketReleaseRate,
                            int ticketRetrievalRate,
                            int maximumTicketsCapacity) {

    public void save() {
        System.out.println("Saving configuration...");
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("TicketSystem.json")) {
            writer.write(gson.toJson(this));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Configuration saved!");
    }
}
