package com.tingcore.cdc.charging.model;

import java.util.Objects;

public class CustomerKeyId {
    public final Long value;

    public CustomerKeyId(final Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerKeyId that = (CustomerKeyId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
