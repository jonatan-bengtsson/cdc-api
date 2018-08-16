package com.tingcore.cdc.payments.model;

public enum ChargingSessionStatus {
    CREATED,
    WAITING_TO_START,
    STARTED,
    WAITING_TO_STOP,
    STOPPED,
    COMPLETE,
    FAILED
}
