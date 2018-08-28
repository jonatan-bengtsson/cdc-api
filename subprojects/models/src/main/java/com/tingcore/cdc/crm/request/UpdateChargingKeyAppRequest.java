package com.tingcore.cdc.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

public class UpdateChargingKeyAppRequest {
    private String chargingKeyName;

    public UpdateChargingKeyAppRequest(final String chargingKeyName) {

        this.chargingKeyName = chargingKeyName;
    }

    public UpdateChargingKeyAppRequest() {
    }

    @JsonProperty(FieldConstant.CHARGING_KEY_NAME)
    @Size(max = 50)
    @ApiModelProperty(value ="The name of the charging key", example = "My blue tag")
    public String getChargingKeyName() {
        return chargingKeyName;
    }

    public void setChargingKeyName(String chargingKeyName) {
        this.chargingKeyName = chargingKeyName;
    }
}
