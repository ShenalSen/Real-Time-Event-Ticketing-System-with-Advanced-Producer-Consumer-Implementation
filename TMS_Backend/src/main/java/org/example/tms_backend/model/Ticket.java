package org.example.tms_backend.model;

import lombok.Data;

@Data
public class Ticket {
    private int id;
    private String status; // e.g., "Added" or "Removed"
}
