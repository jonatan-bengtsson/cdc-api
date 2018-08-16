package com.tingcore.cdc.payments.model;

import io.swagger.annotations.ApiModel;

import java.time.Instant;

@ApiModel
public class ChargingSessionEvent {
    public final String chargingSessionId;
    public final String chargingSessionEventId;
    public final Instant time;
    public final ChargingSessionEventNature nature;

    public ChargingSessionEvent(final String chargingSessionId,
                                final String chargingSessionEventId,
                                final Instant time,
                                final ChargingSessionEventNature nature) {
        this.chargingSessionId = chargingSessionId;
        this.chargingSessionEventId = chargingSessionEventId;
        this.time = time;
        this.nature = nature;
    }

    public String getChargingSessionId() {
        return chargingSessionId;
    }

    public String getChargingSessionEventId() {
        return chargingSessionEventId;
    }

    public Instant getTime() {
        return time;
    }

    public ChargingSessionEventNature getNature() {
        return nature;
    }
}
