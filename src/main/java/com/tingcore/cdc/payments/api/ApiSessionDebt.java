package com.tingcore.cdc.payments.api;

import io.swagger.annotations.ApiModel;

import java.time.Instant;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@ApiModel
public class ApiSessionDebt {
    private final String sessionId;
    private final String currency;
    private final Long minorUnitsInclVat;
    private final Instant timestamp;
    private final double vatPercent;

    public ApiSessionDebt(final String sessionId,
                          final String currency,
                          final Long minorUnitsInclVat,
                          final Instant timestamp,
                          final double vatPercent) {
        this.sessionId = notBlank(sessionId);
        this.currency = notBlank(currency);
        this.minorUnitsInclVat = notNull(minorUnitsInclVat);
        this.timestamp = notNull(timestamp);
        this.vatPercent = notNull(vatPercent);
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getCurrency() {
        return currency;
    }

    public Long getMinorUnitsInclVat() {
        return minorUnitsInclVat;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public double getVatPercent() {
        return vatPercent;
    }
}
