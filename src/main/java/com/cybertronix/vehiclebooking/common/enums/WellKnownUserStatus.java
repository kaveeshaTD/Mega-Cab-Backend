package com.cybertronix.vehiclebooking.common.enums;

public enum WellKnownUserStatus {
    ACTIVE(1),
    PENDING(2),
    DELETED(3);

    private final int value;

    WellKnownUserStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
