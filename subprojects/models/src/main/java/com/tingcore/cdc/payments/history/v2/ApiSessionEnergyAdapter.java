package com.tingcore.cdc.payments.history.v2;


import com.tingcore.cdc.payments.history.ApiSessionEnergy;

import static org.apache.commons.lang3.Validate.notNull;

public class ApiSessionEnergyAdapter implements ApiSessionEnergy {

    private final com.tingcore.sessions.history.api.v1.ApiSessionEnergy original;

    public ApiSessionEnergyAdapter(com.tingcore.sessions.history.api.v1.ApiSessionEnergy original) {
        this.original = notNull(original);
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
