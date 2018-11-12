package com.tingcore.cdc.payments.history.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.payments.history.ApiAmount;
import com.tingcore.cdc.payments.history.ApiChargeHistory;
import com.tingcore.cdc.payments.history.ApiSessionEnergy;
import com.tingcore.sessions.history.api.v1.ApiChargePointSiteInfo;
import com.tingcore.sessions.history.api.v1.ApiComputedInfo;
import com.tingcore.sessions.history.api.v1.ApiHistoryLine;

import java.time.Instant;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.notNull;

public class ApiChargeHistoryAdapter implements ApiChargeHistory {

    private final com.tingcore.sessions.history.api.v1.ApiSessionHistory original;

    public ApiChargeHistoryAdapter(com.tingcore.sessions.history.api.v1.ApiSessionHistory original) {
        this.original = notNull(original);
    }

    @Override
    public Long getSessionId() {
        return ofNullable(original.getHistory())
                .map(ApiHistoryLine::getSessionId)
                .orElse(null);
    }

    @Override
    public ApiAmount getPrice() {
        return ofNullable(original.getHistory())
                .map(ApiHistoryLine::getPrice)
                .map(ApiAmountAdapter::new)
                .orElse(null);
    }

    @Override
    public Long getConnectorId() {
        return ofNullable(original.getHistory())
                .map(ApiHistoryLine::getConnectorId)
                .orElse(null);
    }

    @Override
    public Long getStartTime() {
        return ofNullable(original.getHistory())
                .map(ApiHistoryLine::getStartTime)
                .map(Instant::toEpochMilli)
                .orElse(null);
    }

    @Override
    public String getSite() {
        return ofNullable(original.getHistory())
                .map(ApiHistoryLine::getChargePointSiteInfo)
                .map(ApiChargePointSiteInfo::getChargePointSiteName)
                .orElse(null);
    }

    @Override
    public String getBalanceStatus() {
        return ofNullable(original.getComputedInfo())
                .map(ApiComputedInfo::getBalanceStatus)
                .orElse(null);
    }

    @Override
    public String getSessionStatus() {
        return ofNullable(original.getComputedInfo())
                .map(ApiComputedInfo::getSessionStatus)
                .orElse(null);
    }

    @Override
    public Long getOrganizationId() {
        return ofNullable(original.getHistory())
                .map(ApiHistoryLine::getOrganizationId)
                .orElse(null);
    }

    @Override
    public ApiSessionEnergy getEnergy() {
        return ofNullable(original.getHistory())
                .map(ApiHistoryLine::getEnergy)
                .map(ApiSessionEnergyAdapter::new)
                .orElse(null);
    }
}
