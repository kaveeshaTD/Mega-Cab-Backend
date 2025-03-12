package com.cybertronix.vehiclebooking.common.enums;

public enum WellKnownUserRole {
    USER(1),
    ADMIN(2),
    DRIVER(3);

    private final int value;

    WellKnownUserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
