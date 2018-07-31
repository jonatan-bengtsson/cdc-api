package com.tingcore.cdc.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

public class UpdateChargingKeyAppRequest {
    private String chargingKeyName;

    public UpdateChargingKeyAppRequest(String chargingKeyName) {
        this.chargingKeyName = chargingKeyName;
    }

    @JsonProperty(FieldConstant.CHARGING_KEY_NAME)
    @ApiModelProperty(value ="The name of the charging key", example = "My blue tag")
    public String getChargingKeyName() {
        return chargingKeyName;
    }

    public void setChargingKeyName(String chargingKeyName) {
        this.chargingKeyName = chargingKeyName;
    }
}
