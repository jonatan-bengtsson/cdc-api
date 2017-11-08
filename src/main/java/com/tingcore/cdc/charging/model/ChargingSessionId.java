package com.tingcore.cdc.charging.model;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Objects;

public class ChargingSessionId {
    public final Long value;

    public ChargingSessionId(Long value) {
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
        ChargingSessionId that = (ChargingSessionId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
