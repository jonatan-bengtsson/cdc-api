package com.tingcore.cdc.charging.mapper;

import com.tingcore.charging.assets.model.CompleteChargePoint;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.charging.assets.model.Connector;
import com.tingcore.charging.operations.model.ChargePointStatusRequest;
import com.tingcore.charging.operations.model.StatusBatchRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OperationsApiMapper {

    public StatusBatchRequest toBatchStatusRequest(CompleteChargePointSite ccps) {
        return new StatusBatchRequest()
                .statusRequests(ccps.getChargePoints().stream()
                        .map(this::toChargePointStatusRequest)
                        .collect(Collectors.toList())
                );
    }

    public StatusBatchRequest toBatchStatusRequest(Stream<CompleteChargePointSite> ccpss) {
        return new StatusBatchRequest()
                .statusRequests(
                        ccpss.map(CompleteChargePointSite::getChargePoints)
                                .flatMap(cps -> cps.stream().map(this::toChargePointStatusRequest))
                        .collect(Collectors.toList())
                );
    }

    public ChargePointStatusRequest toChargePointStatusRequest(CompleteChargePoint cp) {
        return new ChargePointStatusRequest()
                .chargePointId(cp.getId())
                .connectorIds(cp.getConnectors().stream().map(Connector::getId).collect(Collectors.toList()));
    }

}
