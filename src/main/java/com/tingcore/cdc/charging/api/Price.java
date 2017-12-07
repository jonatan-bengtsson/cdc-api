package com.tingcore.cdc.charging.api;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class Price {
    public final Amount inclVat;
    public final Amount exclVat;
    public final String currency;

    public Price(final Amount inclVat,
                 final Amount exclVat,
                 final String currency) {
        this.inclVat = notNull(inclVat);
        this.exclVat = notNull(exclVat);
        this.currency = notBlank(currency);
    }

    public static class Amount {
        public final Long centAmount;
        public final String formattedAmount;

        public Amount(final Long centAmount,
                      final String formattedAmount) {
            this.centAmount = notNull(centAmount);
            this.formattedAmount = notBlank(formattedAmount);
        }
    }
}
