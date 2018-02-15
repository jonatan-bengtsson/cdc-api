package com.tingcore.cdc.charging.model;

import java.time.Instant;

public class ChargingSessionBuilder {
    private ChargingSessionId id;
    private CustomerKeyId customerKeyId;
    private Price price;
    private Instant startTime;
    private Instant endTime;
    private ChargingSessionStatus status;
    private ConnectorId connectorId;
    private ChargePointId chargePointId;
    private ChargeSiteId chargeSiteId;

    public ChargingSessionBuilder setId(final ChargingSessionId id) {
        this.id = id;
        return this;
    }

    public ChargingSessionBuilder setCustomerKeyId(final CustomerKeyId customerKeyId) {
        this.customerKeyId = customerKeyId;
        return this;
    }

    public ChargingSessionBuilder setPrice(final Price price) {
        this.price = price;
        return this;
    }

    public ChargingSessionBuilder setStartTime(final Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public ChargingSessionBuilder setEndTime(final Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public ChargingSessionBuilder setStatus(final ChargingSessionStatus status) {
        this.status = status;
        return this;
    }

    public ChargingSessionBuilder setConnectorId(final ConnectorId connectorId) {
        this.connectorId = connectorId;
        return this;
    }

    public ConnectorId getConnectorId() {
        return this.connectorId;
    }

    public ChargingSessionBuilder setChargePointId(final ChargePointId chargePointId) {
        this.chargePointId = chargePointId;
        return this;
    }

    public ChargingSessionBuilder setChargeSiteId(final ChargeSiteId chargeSiteId) {
        this.chargeSiteId = chargeSiteId;
        return this;
    }

    public ChargingSession build() {
        return new ChargingSession(id, customerKeyId, price, startTime, endTime, status, connectorId, chargePointId, chargeSiteId);
    }
}