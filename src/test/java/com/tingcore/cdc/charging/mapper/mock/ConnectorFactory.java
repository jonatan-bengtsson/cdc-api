package com.tingcore.cdc.charging.mapper.mock;

import com.tingcore.charging.assets.model.Connector;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;

public class ConnectorFactory {

    public static ConnectorStatusResponse createConnectorStatus(Long connectorId, boolean busy, boolean outOfOrder, boolean reserved) {
        return new ConnectorStatusResponse()
                .connectorId(connectorId)
                .busy(busy)
                .outOfOrder(outOfOrder)
                .reserved(reserved);
    }


    public static Connector createConnector(Long chargePointId, Long connectorId, Long connectorModelId) {
        return createConnector(chargePointId, connectorId, connectorModelId, Connector.ConnectorTypeEnum.TYPE2, 20_000);
    }

    public static Connector createConnector(Long chargePointId, Long connectorId, Long connectorModelId, Connector.ConnectorTypeEnum type, double power) {
        return new Connector()
                .id(connectorId)
                .chargePointId(chargePointId)
                .connectorModelId(connectorModelId)
                .connectorType(type)
                .power(power)
                .connectorNumber(1)
                .current(120d)
                .mode(Connector.ModeEnum.MODE1)
                .operationalStatus(Connector.OperationalStatusEnum.OPERATIONAL)
                .voltage(40d);
    }

}
