package com.tingcore.cdc.charging.model;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ConnectorPrice that = (ConnectorPrice) o;
        return Objects.equals(connectorId, that.connectorId) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectorId, price);
    }

    @Override
    public String toString() {
        return "ConnectorPrice{" +
                "connectorId=" + connectorId +
                ", price='" + price + '\'' +
                '}';
    }
}
