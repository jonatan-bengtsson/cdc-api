package com.tingcore.cdc.charging.repository;

import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.ConnectorPrice;

import java.util.List;

public interface PriceStore {

    public List<ConnectorPrice> priceForConnectors(final List<ConnectorId> connectorIds);

}
