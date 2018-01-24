package com.tingcore.cdc.charging.api;

import com.tingcore.cdc.charging.model.ChargePointId;
import com.tingcore.cdc.charging.model.ChargeSiteId;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.crm.model.CustomerKey;
import io.swagger.annotations.ApiModel;

import java.time.Instant;

import static org.apache.commons.lang3.Validate.notNull;

@ApiModel
public class ChargingSession {
    public final String chargingSessionId;
    public final CustomerKey customerKey;
    public final Price price;
    public final Instant startTime;
    public final Instant endTime;
    public final ChargingSessionStatus status;
    public final ConnectorId connectorId;
    public final ChargePointId chargePointId;
    public final ChargeSiteId chargeSiteId;

    public ChargingSession(final String chargingSessionId,
                           final CustomerKey customerKey,
                           final Price price,
                           final Instant startTime,
                           final Instant endTime,
                           final ChargingSessionStatus status,
                           final ConnectorId connectorId,
                           final ChargePointId chargePointId,
                           final ChargeSiteId chargeSiteId) {
        this.chargingSessionId = notNull(chargingSessionId);
        this.customerKey = notNull(customerKey);
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = notNull(status);
        this.connectorId = connectorId;
        this.chargePointId = chargePointId;
        this.chargeSiteId = chargeSiteId;
    }
}
