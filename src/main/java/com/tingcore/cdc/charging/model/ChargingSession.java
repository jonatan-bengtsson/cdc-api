package com.tingcore.cdc.charging.model;

import java.time.Instant;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class ChargingSession {
    public final ChargingSessionId id;
    public final CustomerKeyId customerKeyId;
    public final Instant startTime;
    public final Instant endTime;
    public final ChargingSessionStatus status;

    public ChargingSession(final ChargingSessionId id,
                           final CustomerKeyId customerKeyId,
                           final Instant startTime,
                           final Instant endTime,
                           final ChargingSessionStatus status) {
        this.id = notNull(id);
        this.customerKeyId = notNull(customerKeyId);
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = notNull(status);
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
