package com.tingcore.cdc.payments.history.v1;

import com.tingcore.cdc.payments.history.ApiSessionEnergy;

public class ApiSessionEnergyAdapter implements ApiSessionEnergy {

    private final com.tingcore.payments.cpo.model.ApiSessionEnergy original;

    public ApiSessionEnergyAdapter(com.tingcore.payments.cpo.model.ApiSessionEnergy original) {
        this.original = original;
    }

    @Override
    public String getValue() {
        return original.getValue();
    }

    @Override
    public String getUnit() {
        return original.getUnit();
    }
}
