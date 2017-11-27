package com.tingcore.cdc.charging.api;

public enum ChargingSessionStatus {
    CREATED,
    WAITING_TO_START,
    STARTED,
    WAITING_TO_COMPLETE,
    COMPLETED,
    CLEARED,
    FAILED
}
