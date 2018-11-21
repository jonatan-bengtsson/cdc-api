package com.tingcore.cdc.payments.history.v1;

import com.tingcore.cdc.payments.history.ApiAmount;
import com.tingcore.cdc.payments.history.ApiChargeHistory;
import com.tingcore.cdc.payments.history.ApiSessionEnergy;

import static java.util.Optional.ofNullable;

public class ApiChargeHistoryAdapter implements ApiChargeHistory {

    private final com.tingcore.payments.cpo.model.ApiChargeHistory original;

    public ApiChargeHistoryAdapter(com.tingcore.payments.cpo.model.ApiChargeHistory original) {
        this.original = original;
    }

    @Override
    public Long getSessionId() {
        return original.getSessionId();
    }

    @Override
    public ApiAmount getPrice() {
        return ofNullable(original.getPrice())
                .map(ApiAmountAdapter::new)
                .orElse(null);
    }

    @Override
    public Long getConnectorId() {
        return original.getConnectorId();
    }

    @Override
    public Long getStartTime() {
        return original.getStartTime();
    }

    @Override
    public String getSite() {
        return original.getSite();
    }

    @Override
    public String getBalanceStatus() {
        return original.getBalanceStatus().getValue();
    }

    @Override
    public Long getOrganizationId() {
        return original.getOrganizationId();
    }

    @Override
    public String getSessionStatus() { return null; }

    @Override
    public ApiSessionEnergy getEnergy() {
        return ofNullable(original.getEnergy())
                .map(ApiSessionEnergyAdapter::new)
                .orElse(null);
    }
}
