package io.github.Shenalsen;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class TicketSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TicketSystem ticketSystem = new TicketSystem();
    private static int userThreadStatus;
    private static TicketPool ticketsPool;
    private static boolean keepRunning = true;


    public static void main(String[] args) throws InterruptedException {
        System.out.println("""
                *************************
                ***   Ticket System   ***
                *************************
                
                # Configuration""");

        int totalNumberOfTickets;
        int ticketReleaseRate;
        int ticketRetrievalRate;
        int maximumTicketsCapacity;

        maximumTicketsCapacity = getValidIntInput("Enter maximum capacity of the tickets pool: ", 1, Integer.MAX_VALUE);
        totalNumberOfTickets = getValidIntInput("Enter total number of tickets in the pool: ", 0, maximumTicketsCapacity);
        ticketReleaseRate = getValidIntInput("Enter ticket release rate of the pool: ", 1, 30);
        ticketRetrievalRate = getValidIntInput("Enter ticket retrieval rate of the pool: ", 1, 30);

        Configuration configuration = new Configuration(totalNumberOfTickets, ticketReleaseRate,
                ticketRetrievalRate, maximumTicketsCapacity);
        ticketsPool = new TicketPool(configuration);

        while (true) {
            System.out.print("""
                    \n# Main Menu
                    1 - Save Configuration
                    2 - Add Vendors
                    3 - Add Customers
                    4 - Simulate System
                    0 - Exit
                    > """);
            char option = scanner.next().charAt(0);
            switch (option) {
                case '1' -> configuration.save();
                case '2' -> {
                    int vendorCount = getValidIntInput("\n# Add Vendors\nEnter the count of vendors to be added: ", 1, Integer.MAX_VALUE);
                    Vendor.addVendors(vendorCount);
                    System.out.println("Success!");
                }
                case '3' -> {
                    int customerCount = getValidIntInput("\n# Add Customers\nEnter the count of customers to be added: ", 1, Integer.MAX_VALUE);
                    Customer.addCustomers(customerCount);
                    System.out.println("Success!");
                }
                case '4' -> ticketSystem.simulateSystem();
                case '0' -> {
                    keepRunning = false; // Stop the simulation loop
                    synchronized (ticketSystem) {
                        ticketSystem.notifyAll(); // Notify any waiting threads
                    }
                    System.exit(0);
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private synchronized void simulateSystem() throws InterruptedException {

        if (Vendor.getVendors().isEmpty()) {
            System.out.println("Error: No vendors have been added to the system. Please add vendors before simulating.");
            return;
        }
        if (Customer.getCustomers().isEmpty()) {
            System.out.println("Error: No customers have been added to the system. Please add customers before simulating.");
            return;
        }

        keepRunning = true;
        while (keepRunning) {
            Thread vendorThread = Thread.ofVirtual().start(() -> {
                for (Vendor vendor : Vendor.getVendors()) {
                    Thread.ofVirtual().start(vendor);
                }
            });

            Thread customerThread = Thread.ofVirtual().start(() -> {
                for (Customer customer : Customer.getCustomers()) {
                    Thread.ofVirtual().start(customer);
                }
            });

            vendorThread.join();
            customerThread.join();

            if (!keepRunning) {
                break; // Exit the loop if the flag is set to false
            }

            wait();

        }
        // Notify any waiting threads to avoid indefinite blocking
        notifyAll();
        System.out.println("Simulation stopped.");
    }

    public static TicketSystem getTicketSystem() {
        return ticketSystem;
    }

    public static TicketPool getTicketsPool() {
        return ticketsPool;
    }

    public synchronized void resumeMainThread() {
        userThreadStatus++;
        if (userThreadStatus == (Vendor.getVendors().size() + Customer.getCustomers().size())) {
            notify();
        }
    }

    private static int getValidIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.printf("Input must be between %d and %d. Please try again.%n", min, max);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }
    }
}
