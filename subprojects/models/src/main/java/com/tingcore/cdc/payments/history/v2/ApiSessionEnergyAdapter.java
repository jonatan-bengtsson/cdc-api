package com.tingcore.cdc.payments.history.v2;

import com.tingcore.cdc.payments.history.ApiSessionEnergy;
import com.tingcore.payments.sessionstasher.models.v1.SessionEnergy;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.apache.commons.lang3.Validate.notNull;

public class ApiSessionEnergyAdapter implements ApiSessionEnergy {
    private static final NumberFormat NUMBER_FORMAT = DecimalFormat.getNumberInstance(new Locale("sv","SE"));

    private final SessionEnergy sessionEnergy;

    public ApiSessionEnergyAdapter(final SessionEnergy sessionEnergy) {
        this.sessionEnergy = notNull(sessionEnergy);
    }

    @Override
    public String getValue() {
        final long value = sessionEnergy.getValue();
        final double v = sessionEnergy.getUnit().equalsIgnoreCase("Wh") ? (value / 1000d) : value;
        return NUMBER_FORMAT.format(v);
    }

    @Override
    public String getUnit() {
        final String unit = sessionEnergy.getUnit();
        return unit.equalsIgnoreCase("Wh") ? "kWh" : unit;
    }
}
