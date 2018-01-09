package com.tingcore.cdc.charging.mapper.mock;

import com.tingcore.charging.operations.model.ConnectorStatusResponse;

public class ConnectorStatusResponseFactory {

    public static ConnectorStatusResponse create(Long connectorId, ConnectorStatusResponse.ConnectorStatusEnum connectorStatusEnum) {
        ConnectorStatusResponse connectorStatusResponse = new ConnectorStatusResponse();
        connectorStatusResponse.connectorId(connectorId);
        connectorStatusResponse.setConnectorStatus(connectorStatusEnum);

        return connectorStatusResponse;
    }

}
