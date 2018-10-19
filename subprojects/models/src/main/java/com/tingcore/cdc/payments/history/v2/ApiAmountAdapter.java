package com.tingcore.cdc.payments.history.v2;


import com.tingcore.cdc.payments.history.ApiAmount;

import java.math.BigDecimal;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.notNull;

public class ApiAmountAdapter implements ApiAmount {

    private final com.tingcore.sessions.history.api.v1.ApiAmount original;

    public ApiAmountAdapter(com.tingcore.sessions.history.api.v1.ApiAmount original) {
        this.original = notNull(original);
    }

    @Override
    public String getInclVat() {
        return original.getAmountIncl();
    }

    @Override
    public String getExclVat() {
        return original.getAmountExcl();
    }

    @Override
    public String getCurrency() {
        return original.getCurrency();
    }

    @Override
    public String getTax() {
        return ofNullable(original.getTax()).map(BigDecimal::toString).orElse(null);
    }
}
