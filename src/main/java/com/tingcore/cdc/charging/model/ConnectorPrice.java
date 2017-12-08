package com.tingcore.cdc.charging.model;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class ConnectorPrice {
    public final ConnectorId connectorId;
    public final String price;

    public ConnectorPrice(final ConnectorId connectorId,
                          final String price) {
        this.connectorId = notNull(connectorId);
        this.price = notBlank(price);
    }
}
