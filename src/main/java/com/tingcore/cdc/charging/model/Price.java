package com.tingcore.cdc.charging.model;

import static org.apache.commons.lang3.Validate.notBlank;

public class Price {
    public final String exclVat;
    public final String inclVat;
    public final String currency;

    public Price(final String exclVat,
                 final String inclVat,
                 final String currency) {
        this.exclVat = notBlank(exclVat);
        this.inclVat = notBlank(inclVat);
        this.currency = notBlank(currency);
    }
}
