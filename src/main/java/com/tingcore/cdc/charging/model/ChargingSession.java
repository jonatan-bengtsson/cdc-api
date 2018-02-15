package com.tingcore.cdc.charging.model;

import java.time.Instant;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class ChargingSession {
    public final ChargingSessionId id;
    public final CustomerKeyId customerKeyId;
    public final Price price;
    public final Instant startTime;
    public final Instant endTime;
    public final ChargingSessionStatus status;
    public final ConnectorId connectorId;
    public final ChargePointId chargePointId;
    public final ChargeSiteId chargeSiteId;

    public ChargingSession(final ChargingSessionId id,
                           final CustomerKeyId customerKeyId,
                           final Price price,
                           final Instant startTime,
                           final Instant endTime,
                           final ChargingSessionStatus status,
                           final ConnectorId connectorId,
                           final ChargePointId chargePointId,
                           final ChargeSiteId chargeSiteId) {
        this.id = notNull(id);
        this.customerKeyId = notNull(customerKeyId);
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = notNull(status);
        this.connectorId = connectorId;
        this.chargePointId = chargePointId;
        this.chargeSiteId = chargeSiteId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChargingSession that = (ChargingSession) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
