package com.tingcore.cdc.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class UserId {
    public final String value;

    public UserId(final String value) {
        this.value = notNull(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
