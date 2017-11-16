package com.tingcore.cdc.charging.api;

import io.swagger.annotations.ApiModel;

import java.time.Instant;

import static org.apache.commons.lang3.Validate.notNull;

@ApiModel
public class ChargingSession {
    public final String chargingSessionId;
    public final CustomerKey customerKey;
    public final Instant startTime;
    public final Instant endTime;
    public final ChargingSessionStatus status;

    public ChargingSession(final String chargingSessionId,
                           final CustomerKey customerKey,
                           final Instant startTime,
                           final Instant endTime,
                           final ChargingSessionStatus status) {
        this.chargingSessionId = notNull(chargingSessionId);
        this.customerKey = notNull(customerKey);
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = notNull(status);
    }
}
