package com.tingcore.cdc.charging.mapper.mock;

import com.tingcore.charging.assets.model.CompleteChargePoint;
import com.tingcore.charging.assets.model.Connector;
import com.tingcore.charging.operations.model.ChargePointStatusResponse;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;

import java.util.List;

public class ChargePointFactory {

    public static CompleteChargePoint createCompleteChargePoint(Long chargePointId, Long chargePointTypeId, List<Connector> connectors, CompleteChargePoint.OperationalStatusEnum status) {
        return new CompleteChargePoint()
                .id(chargePointId)
                .assetName(String.format("ASSET%d", chargePointId))
                .chargePointTypeId(chargePointTypeId)
                .connectors(connectors)
                .operationalStatus(status);
    }

    public static ChargePointStatusResponse createBasicChargePointStatus(Long chargePointId, boolean online) {
        return new ChargePointStatusResponse().chargePointId(chargePointId).online(online);
    }

    public static ChargePointStatusResponse createChargePointStatus(Long chargePointId, boolean online, List<ConnectorStatusResponse> connectorStatuses) {
        return new ChargePointStatusResponse()
                .chargePointId(chargePointId)
                .online(online)
                .connectorStatusResponses(connectorStatuses);
    }

}
