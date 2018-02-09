package com.tingcore.cdc.charging.utils;

import com.tingcore.charging.assets.model.BasicChargePoint;
import com.tingcore.charging.assets.model.ChargePoint;
import com.tingcore.charging.assets.model.ChargePointSetting;
import com.tingcore.charging.assets.model.ChargePointEntity;
import com.tingcore.charging.assets.model.CompleteChargePoint;
import com.tingcore.charging.assets.model.ConnectorEntity;
import com.tingcore.charging.assets.model.EntityMetadata;
import com.tingcore.charging.operations.model.ChargePointStatusResponse;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;

import java.util.List;

public class ChargePointDataUtils {

    public static CompleteChargePoint createCompleteChargePoint(Long chargePointId, Long chargePointModelId, List<ConnectorEntity> connectors, BasicChargePoint.OperationalStatusEnum status) {

        BasicChargePoint basicChargePoint = new BasicChargePoint()
                .assetName(String.format("ASSET%d", chargePointId))
                .chargePointModelId(chargePointModelId)
                .operationalStatus(status);

        ChargePointSetting settings = new ChargePointSetting();

        ChargePoint chargePoint  = new ChargePoint()
                .basicChargePoint(basicChargePoint)
                .chargePointSetting(settings);

        EntityMetadata meta = new EntityMetadata()
                .id(chargePointId);

        ChargePointEntity chargePointEntity = new ChargePointEntity()
                .data(chargePoint)
                .metadata(meta);

        return new CompleteChargePoint()
                .chargePointEntity( chargePointEntity)
                .connectorEntities(connectors);
    }
    
    public static ChargePointStatusResponse createChargePointStatus(Long chargePointId, boolean online, List<ConnectorStatusResponse> connectorStatuses) {
        return new ChargePointStatusResponse()
                .chargePointId(chargePointId)
                .online(online)
                .connectorStatusResponses(connectorStatuses);
    }

}
