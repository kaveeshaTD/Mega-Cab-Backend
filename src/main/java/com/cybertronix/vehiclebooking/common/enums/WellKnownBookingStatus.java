package com.cybertronix.vehiclebooking.common.enums;

public enum WellKnownBookingStatus {
    PENDING(1),
    STARTED(2),
    COMPLETED(3),
    CANCELLED(4);

    private final int value;

    WellKnownBookingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getStatus() {
        switch (value) {
            case 1:
                return "PENDING";
            case 2:
                return "STARTED";
            case 3:
                return "COMPLETED";
            case 4:
                return "CANCELLED";
            default:
                return "UNKNOWN";
        }
    }
    
}
