package com.tingcore.cdc.charging.model;

import java.time.Instant;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class ChargingSessionEvent {
    public final ChargingSessionId sessionId;
    public final ChargingSessionEventId eventId;
    public final Instant time;
    public final ChargingSessionEventNature nature;

    public ChargingSessionEvent(final ChargingSessionId sessionId,
                                final ChargingSessionEventId eventId,
                                final Instant time,
                                final ChargingSessionEventNature nature) {
        this.sessionId = notNull(sessionId);
        this.eventId = notNull(eventId);
        this.time = notNull(time);
        this.nature = notNull(nature);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChargingSessionEvent that = (ChargingSessionEvent) o;
        return Objects.equals(sessionId, that.sessionId) &&
                Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, eventId);
    }
}
