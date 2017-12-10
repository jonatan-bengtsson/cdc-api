package com.tingcore.cdc.charging.mapper.mock;

import com.tingcore.charging.assets.model.BasicConnector;
import com.tingcore.charging.assets.model.Connector;
import com.tingcore.charging.assets.model.ConnectorCapability;
import com.tingcore.charging.assets.model.ConnectorEntity;
import com.tingcore.charging.assets.model.EntityMetadata;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;

public class ConnectorFactory {

    public static ConnectorStatusResponse createConnectorStatus(Long connectorId, boolean busy, boolean outOfOrder, boolean reserved) {
        return new ConnectorStatusResponse()
                .connectorId(connectorId)
                .busy(busy)
                .outOfOrder(outOfOrder)
                .reserved(reserved);
    }


    public static ConnectorEntity createConnector(Long chargePointId, Long connectorId, Long connectorModelId, int connectorNumber) {
        return createConnector(chargePointId, connectorId, connectorModelId, connectorNumber, BasicConnector.ConnectorTypeEnum.TYPE2, 20_000);
    }

    public static ConnectorEntity createConnector(Long chargePointId, Long connectorId, Long connectorModelId, int connectorNumber, BasicConnector.ConnectorTypeEnum type, double power) {
        BasicConnector basicConnector = new BasicConnector()
                .chargePointId(chargePointId)
                .connectorModelId(connectorModelId)
                .connectorNumber(connectorNumber)
                .connectorType(type)
                .operationalStatus(BasicConnector.OperationalStatusEnum.OPERATIONAL);
        ConnectorCapability connectorCapability = new ConnectorCapability()
                .power(power)
                .current(120d)
                .voltage(40d)
                .mode(ConnectorCapability.ModeEnum.MODE1);

        Connector connector = new Connector()
                .basicConnector(basicConnector)
                .connectorCapability(connectorCapability);
        EntityMetadata meta = new EntityMetadata()
                .id(connectorId);

        return new ConnectorEntity()
                .data(connector)
                .metadata(meta);
    }

}
