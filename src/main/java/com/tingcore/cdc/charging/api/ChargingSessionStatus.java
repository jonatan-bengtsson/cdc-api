package com.tingcore.cdc.charging.api;

public enum ChargingSessionStatus {
    PENDING,
    CREATED,
    WAITING_TO_START,
    TIMEOUT_WAITING_TO_START,
    STARTED,
    WAITING_TO_COMPLETE,
    TIMEOUT_WAITING_TO_COMPLETE,
    COMPLETED,
    FAILED
}
