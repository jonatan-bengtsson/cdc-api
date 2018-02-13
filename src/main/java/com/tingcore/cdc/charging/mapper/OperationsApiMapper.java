package com.tingcore.cdc.charging.mapper;

import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.charging.operations.model.IdList;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperationsApiMapper {

    public static IdList toBatchStatusRequest(CompleteChargePointSite ccps) {
        return new IdList().ids(ccps.getChargePoints().stream().map(cp -> cp.getChargePointEntity().getMetadata().getId())
                               .collect(
                Collectors.toList()));
    }

    public static IdList toBatchStatusRequest(Stream<CompleteChargePointSite> ccpss) {
        return new IdList().ids(
                        ccpss.map(CompleteChargePointSite::getChargePoints)
                                .flatMap(cps -> cps.stream().map(cp -> cp.getChargePointEntity().getMetadata().getId()))
                        .collect(Collectors.toList())
                );

    }

}
