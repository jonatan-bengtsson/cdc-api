package com.tingcore.cdc.payments.pricing.model;

import static org.apache.commons.lang3.Validate.notBlank;

public enum PriceUnit {
    MIN("min"),
    H("h"),
    KWH("kWh");

    public final String unit;

    PriceUnit(final String unit) {
        this.unit = notBlank(unit);
    }

    @Override
    public String toString() {
        return unit;
    }
}
