package com.tingcore.cdc.charging.api;

import io.swagger.annotations.ApiModel;

import java.time.Instant;

import static org.apache.commons.lang3.Validate.notNull;

@ApiModel
public class ChargingSession {
    public final String chargingSessionId;
    public final Long customerKeyId;
    public final Price price;
    public final Instant startTime;
    public final Instant endTime;
    public final ChargingSessionStatus status;
    public final Long connectorId;
    public final Long chargePointId;
    public final Long chargeSiteId;

    public ChargingSession(final String chargingSessionId,
                           final Long customerKeyId,
                           final Price price,
                           final Instant startTime,
                           final Instant endTime,
                           final ChargingSessionStatus status,
                           final Long connectorId,
                           final Long chargePointId,
                           final Long chargeSiteId) {
        this.chargingSessionId = notNull(chargingSessionId);
        this.customerKeyId = notNull(customerKeyId);
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = notNull(status);
        this.connectorId = connectorId;
        this.chargePointId = chargePointId;
        this.chargeSiteId = chargeSiteId;
    }
}
