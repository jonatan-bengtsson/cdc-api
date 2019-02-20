package com.tingcore.cdc.payments.pricing.model;

import com.tingcore.emp.pricing.profile.response.PriceProfileResponse;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class ConnectorPriceProfile {
    private final long connectorId;
    private final PriceProfileResponse profile;

    public ConnectorPriceProfile(final long connectorId, final PriceProfileResponse profile) {
        this.connectorId = connectorId;
        this.profile = profile;
    }

    public long getConnectorId() {
        return connectorId;
    }

    public Optional<PriceProfileResponse> getProfile() {
        return ofNullable(profile);
    }

    @Override
    public String toString() {
        return "ConnectorPriceProfile{" +
                "connectorId=" + connectorId +
                ", profile=" + profile +
                '}';
    }

}
