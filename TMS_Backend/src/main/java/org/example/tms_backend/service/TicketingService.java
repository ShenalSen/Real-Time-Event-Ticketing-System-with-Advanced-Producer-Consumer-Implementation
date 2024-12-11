package org.example.tms_backend.service;
import org.example.tms_backend.model.Configuration;
import org.example.tms_backend.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class TicketingService {


    @Autowired
    private ConfigurationRepository configurationRepository;
    private final Queue<Integer> ticketPool = new LinkedList<>();

    private boolean isRunning = false;
    private String latestTransaction; // Variable to hold the latest transaction

    private ExecutorService executorService;

    public Configuration getConfigurationByid(Long id) {
        return configurationRepository.findById(id).orElse(null);
    }

    public Configuration saveConfiguration(Configuration configuration) {
        return configurationRepository.save(configuration);
    }

    private void updateLatestTransaction(String message) {
        latestTransaction = message;
        System.out.println(message);
    }

    public String getLatestTransaction() {
        return latestTransaction;
    }

    //endpoint for start system
    public void startSystem(Configuration configuration) {
        if (isRunning) {
            throw new IllegalStateException("System is already running!");
        }

        // Initialize the ticket pool with the total number of tickets
        synchronized (ticketPool) {
            for (int i = 0; i < configuration.getTotalTickets(); i++) {
                ticketPool.add(1);
            }
            System.out.println("Initialized ticket pool with " + configuration.getTotalTickets() + " tickets.");
        }

        isRunning = true;
        executorService = Executors.newFixedThreadPool(2);

        // Vendor thread for releasing tickets
        executorService.execute(() -> {
            while (isRunning) {
                synchronized (ticketPool) {
                    while (ticketPool.size() >= configuration.getMaxTicketCapacity()) {
                        try {
                            ticketPool.wait(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    for (int i = 0; i < configuration.getTicketReleaseRate(); i++) {
                        if (ticketPool.size() < configuration.getMaxTicketCapacity()) {
                            ticketPool.add(1);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                System.out.println("Waitng...");
                            }
                            String message = "VENDOR: Ticket added to the pool (" + ticketPool.size() + "/" + configuration.getMaxTicketCapacity() + ").";
                            updateLatestTransaction(message);
                        }
                    }
                    ticketPool.notifyAll();
                }
            }
        });

        // Customer thread for retrieving tickets
        executorService.execute(() -> {
            while (isRunning) {
                synchronized (ticketPool) {
                    while (ticketPool.isEmpty()) {
                        try {
                            ticketPool.wait(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    for (int i = 0; i < configuration.getCustomerRetrievalRate(); i++) {
                        if (!ticketPool.isEmpty()) {
                            ticketPool.poll();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                System.out.println("Waiting...");
                            }
                            String message = "CUSTOMER: Ticket removed from the pool (" + ticketPool.size() + "/" + configuration.getMaxTicketCapacity() + ").";
                            updateLatestTransaction(message);
                        }
                    }
                    ticketPool.notifyAll();
                }
            }
        });
    }

    //endpoint for stop system
    public void stopSystem() {
        isRunning = false;
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    //endpoint for reset system
    public void resetSystem() {
        synchronized (ticketPool) {
            ticketPool.clear();
        }
    }

    //endpoint for get ticket count
    public int getTicketCount() {
        synchronized (ticketPool) {
            return ticketPool.size();
        }
    }
}

