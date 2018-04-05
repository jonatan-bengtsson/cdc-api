package com.tingcore.cdc.payments.api;

import io.swagger.annotations.ApiModel;

@ApiModel
public class ApiStripeCustomer {
    private final String stripeCustomer;

    public ApiStripeCustomer(final String stripeCustomer) {
        this.stripeCustomer = stripeCustomer;
    }

    public String getStripeCustomer() {
        return stripeCustomer;
    }
}
