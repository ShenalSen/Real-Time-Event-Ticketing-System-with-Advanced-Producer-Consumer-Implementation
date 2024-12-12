package io.github.Shenalsen;

public final class TicketPool {
    private int totalNumberOfTickets;
    private final int ticketReleaseRate;
    private final int ticketRetrievalRate;
    private final int maximumTicketsCapacity;

    public TicketPool(Configuration configuration) {
        totalNumberOfTickets = configuration.totalNumberOfTickets();
        ticketReleaseRate = configuration.ticketReleaseRate();
        ticketRetrievalRate = configuration.ticketRetrievalRate();
        maximumTicketsCapacity = configuration.maximumTicketsCapacity();
    }

    public synchronized void addTickets(Vendor vendor, int count) throws InterruptedException {
        if (count > ticketReleaseRate) {
            System.out.println(vendor.getVendorId() + " : Release rate exceeded (" + count + "); ignoring...");
            return;
        }

        for (int i = 0; i < count; i++) {
            if (totalNumberOfTickets + 1 > maximumTicketsCapacity) {
                System.out.println(vendor.getVendorId() + " : Maximum tickets capacity reached (" +
                        totalNumberOfTickets + "/" + maximumTicketsCapacity + "); waiting...");
                wait();
            }
            totalNumberOfTickets++;
            System.out.println(vendor.getVendorId() + " : Ticket added to the pool ("
                    + totalNumberOfTickets + "/" + maximumTicketsCapacity + ").");
            notify();
        }
    }

    public synchronized void removeTickets(Customer customer, int count) throws InterruptedException {
        if (count > ticketRetrievalRate) {
            System.out.println(customer.getCustomerId() + " : Retrieval rate exceeded (" + count + "); ignoring...");
            return;
        }

        for (int i = 0; i < count; i++) {
            if (totalNumberOfTickets - 1 < 0) {
                System.out.println(customer.getCustomerId() + " : No more tickets to remove (" +
                        totalNumberOfTickets + "/" + maximumTicketsCapacity + "); waiting...");
                wait();
            }
            totalNumberOfTickets--;
            System.out.println(customer.getCustomerId() + " : Ticket removed from the pool ("
                    + totalNumberOfTickets + "/" + maximumTicketsCapacity + ").");
            notify();
        }
    }
}
