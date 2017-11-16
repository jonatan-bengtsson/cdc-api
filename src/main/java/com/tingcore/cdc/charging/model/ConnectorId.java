package com.tingcore.cdc.charging.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class ConnectorId {
    public final Long value;

    public ConnectorId(final Long value) {
        this.value = notNull(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConnectorId that = (ConnectorId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
