package io.github.Shenalsen;

import java.util.ArrayList;
import java.util.List;

public final class Vendor implements Runnable {
    private static long globalVendorId = 0L;
    private static final List<Vendor> vendors = new ArrayList<>();

    private final String vendorId;

    public Vendor() {
        vendorId = "VENDOR-" + globalVendorId++;
    }

    public String getVendorId() {
        return vendorId;
    }

    public static void addVendors(int count) {
        for (int i = 0; i < count; i++) {
            vendors.add(new Vendor());
        }
    }

    public static List<Vendor> getVendors() {
        return vendors;
    }

    @Override
    public void run() {
        for (int i = 5; i > 0; i--) {
            try {
                TicketSystem.getTicketsPool().addTickets(this, i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("System stopped!");
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < 5; i++) {
            try {
                TicketSystem.getTicketsPool().addTickets(this, i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("System stopped!");
                throw new RuntimeException(e);
            }
        }

        TicketSystem.getTicketSystem().resumeMainThread();
    }
}
