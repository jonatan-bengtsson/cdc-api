package com.tingcore.cdc.payments.history;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ApiChargeHistory {

    @JsonProperty("sessionId")
    public Long getSessionId();
    @JsonProperty("price")
    public ApiAmount getPrice();
    @JsonProperty("connectorId")
    public Long getConnectorId();
    @JsonProperty("startTime")
    public Long getStartTime();
    @JsonProperty("site")
    public String getSite();
    @JsonProperty("balanceStatus")
    public String getBalanceStatus();
    @JsonProperty("organizationId")
    public Long getOrganizationId();
    @JsonProperty("energy")
    public ApiSessionEnergy getEnergy();
}
