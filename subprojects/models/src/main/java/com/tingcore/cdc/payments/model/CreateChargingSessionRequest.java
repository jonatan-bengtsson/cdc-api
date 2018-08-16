package com.tingcore.cdc.payments.model;

import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.NotBlank;

@ApiModel
public class CreateChargingSessionRequest {
    @NotBlank
    private String customerKey;
    @NotBlank
    private String chargePointId;
    @NotBlank
    private String connectorId;

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(final String customerKey) {
        this.customerKey = customerKey;
    }

    public String getChargePointId() {
        return chargePointId;
    }

    public void setChargePointId(final String chargePointId) {
        this.chargePointId = chargePointId;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(final String connectorId) {
        this.connectorId = connectorId;
    }
}
