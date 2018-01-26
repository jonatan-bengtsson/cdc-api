package com.tingcore.cdc.charging.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class ChargeSiteId {
    public final Long id;

    public ChargeSiteId(final Long id) {
        this.id = notNull(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", id)
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
        ChargeSiteId that = (ChargeSiteId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
