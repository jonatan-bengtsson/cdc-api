package com.tingcore.cdc.charging.model;

import static org.apache.commons.lang3.Validate.notBlank;

public class PriceInformation {
    public final String price;
    public final String currency;

    public PriceInformation(final String price,
                            final String currency) {
        this.price = notBlank(price);
        this.currency = notBlank(currency);
    }
}
