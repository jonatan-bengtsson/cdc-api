package com.tingcore.cdc.charging.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class TrustedUserId {
  public final Long value;

  public TrustedUserId(final Long value) {
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
    final TrustedUserId trustedUserId = (TrustedUserId) o;
    return Objects.equals(value, trustedUserId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
