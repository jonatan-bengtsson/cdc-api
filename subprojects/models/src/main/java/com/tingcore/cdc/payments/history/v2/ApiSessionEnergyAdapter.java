package com.tingcore.cdc.payments.history.v2;

import com.tingcore.cdc.payments.history.ApiSessionEnergy;
import com.tingcore.payments.sessionstasher.models.v1.SessionEnergy;

import static org.apache.commons.lang3.Validate.notNull;

public class ApiSessionEnergyAdapter implements ApiSessionEnergy {

    private final SessionEnergy sessionEnergy;

    public ApiSessionEnergyAdapter(final SessionEnergy sessionEnergy) {
        this.sessionEnergy = notNull(sessionEnergy);
    }

    @Override
    public String getValue() {
        return Long.toString(sessionEnergy.getValue());
    }

    @Override
    public String getUnit() {
        return sessionEnergy.getUnit();
    }
}
