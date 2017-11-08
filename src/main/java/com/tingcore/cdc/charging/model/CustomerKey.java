package com.tingcore.cdc.charging.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class CustomerKey {
    public final CustomerKeyId id;
    public final String name;

    public CustomerKey(final CustomerKeyId id,
                       final String name) {
        this.id = notNull(id);
        this.name = notBlank(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CustomerKey that = (CustomerKey) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
