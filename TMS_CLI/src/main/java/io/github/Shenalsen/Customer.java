package io.github.Shenalsen;

import java.util.ArrayList;
import java.util.List;

public final class Customer implements Runnable {
    private static long globalCustomerId = 0L;
    private static final List<Customer> customers = new ArrayList<>();

    private final String customerId;

    public Customer() {
        customerId = "CUSTOMER-" + globalCustomerId++;
    }

    public String getCustomerId() {
        return customerId;
    }

    public static void addCustomers(int count) {
        for (int i = 0; i < count; i++) {
            customers.add(new Customer());
        }
    }

    public static List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                TicketSystem.getTicketsPool().removeTickets(this, i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 5; i > 0; i--) {
            try {
                TicketSystem.getTicketsPool().removeTickets(this, i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        TicketSystem.getTicketSystem().resumeMainThread();
    }
}
