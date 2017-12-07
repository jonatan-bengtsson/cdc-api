package com.tingcore.cdc.charging.model;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class Price {
    public final Long exclVat;
    public final Long inclVat;
    public final String currency;

    public Price(final Long exclVat,
                 final Long inclVat,
                 final String currency) {
        this.exclVat = notNull(exclVat);
        this.inclVat = notNull(inclVat);
        this.currency = notBlank(currency);
    }
}
