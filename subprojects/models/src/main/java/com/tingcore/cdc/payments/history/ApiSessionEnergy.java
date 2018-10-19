package com.tingcore.cdc.payments.history;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ApiSessionEnergy {

    @JsonProperty("value")
    public String getValue();
    @JsonProperty("unit")
    public String getUnit();
}
