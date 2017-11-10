package com.tingcore.cdc.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class AuthorizationId {
    public final String value;

    public AuthorizationId(final String value) {
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
        final AuthorizationId that = (AuthorizationId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
