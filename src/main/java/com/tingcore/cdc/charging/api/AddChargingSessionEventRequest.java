package com.tingcore.cdc.charging.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.util.Map;

@ApiModel
public class AddChargingSessionEventRequest {
    private static final String NATURE = "nature";
    private static final String DATA = "data";

    @NotNull
    public final ChargingSessionEventNature nature;
    public final Map<String, Object> data;

    @JsonCreator
    public AddChargingSessionEventRequest(final @JsonProperty(value = NATURE, required = true) ChargingSessionEventNature nature,
                                          final @JsonProperty(value = DATA, required = true) Map<String, Object> data) {
        this.nature = nature;
        this.data = data;
    }
}
