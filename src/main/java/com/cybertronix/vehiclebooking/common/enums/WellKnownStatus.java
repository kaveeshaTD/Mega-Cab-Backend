package com.cybertronix.vehiclebooking.common.enums;


public enum WellKnownStatus {
    ACTIVE(1),
    INACTIVE(2),
    DELETED(3);

    private final int value;

    WellKnownStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
