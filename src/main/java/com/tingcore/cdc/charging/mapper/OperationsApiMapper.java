package com.tingcore.cdc.charging.mapper;

import com.tingcore.charging.assets.model.CompleteChargePoint;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.charging.assets.model.Connector;
import com.tingcore.charging.operations.model.ChargePointStatusRequest;
import com.tingcore.charging.operations.model.StatusBatchRequest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperationsApiMapper {

    public static StatusBatchRequest toBatchStatusRequest(CompleteChargePointSite ccps) {
        return new StatusBatchRequest()
                .statusRequests(ccps.getChargePoints().stream()
                        .map(OperationsApiMapper::toChargePointStatusRequest)
                        .collect(Collectors.toList())
                );
    }

    public static StatusBatchRequest toBatchStatusRequest(Stream<CompleteChargePointSite> ccpss) {
        return new StatusBatchRequest()
                .statusRequests(
                        ccpss.map(CompleteChargePointSite::getChargePoints)
                                .flatMap(cps -> cps.stream().map(OperationsApiMapper::toChargePointStatusRequest))
                        .collect(Collectors.toList())
                );
    }

    public static ChargePointStatusRequest toChargePointStatusRequest(CompleteChargePoint cp) {
        return new ChargePointStatusRequest()
                .chargePointId(cp.getId())
                .connectorIds(cp.getConnectors().stream().map(Connector::getId).collect(Collectors.toList()));
    }

}
