package com.tingcore.cdc.payments.model;

import static org.apache.commons.lang3.Validate.notBlank;

public class Price {
    public final String inclVat;
    public final String exclVat;
    public final String currency;

    public Price(final String inclVat,
                 final String exclVat,
                 final String currency) {
        this.inclVat = notBlank(inclVat);
        this.exclVat = notBlank(exclVat);
        this.currency = notBlank(currency);
    }
}
