package com.tingcore.cdc.payments.history.v1;

import com.tingcore.cdc.payments.history.ApiAmount;

public class ApiAmountAdapter implements ApiAmount {

    private final com.tingcore.payments.cpo.model.ApiAmount original;

    public ApiAmountAdapter(com.tingcore.payments.cpo.model.ApiAmount original) {
        this.original = original;
    }

    @Override
    public String getInclVat() {
        return original.getInclVat();
    }

    @Override
    public String getExclVat() {
        return original.getExclVat();
    }

    @Override
    public String getCurrency() {
        return original.getCurrency();
    }

    @Override
    public String getVat() {
        return original.getVat();
    }
}
