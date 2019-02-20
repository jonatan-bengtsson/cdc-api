package com.tingcore.cdc.payments.pricing.repository;

import com.tingcore.cdc.payments.pricing.model.ConnectorPriceProfile;

import java.util.List;

public interface PricingRepository {
    public List<ConnectorPriceProfile> getCurrentProfileForConnectors(final List<Long> connectorIds);
}
