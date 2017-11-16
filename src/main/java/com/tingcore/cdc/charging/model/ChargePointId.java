package com.tingcore.cdc.charging.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class ChargePointId {
    public final Long value;

    public ChargePointId(final Long value) {
        this.value = notNull(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChargePointId that = (ChargePointId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
