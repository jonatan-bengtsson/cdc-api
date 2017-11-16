package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.model.ChargePoint;
import com.tingcore.cdc.charging.model.ChargePointSite;
import com.tingcore.cdc.charging.model.Connector;
import com.tingcore.charging.assets.model.*;
import com.tingcore.charging.operations.model.ChargePointStatusResponse;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import com.tingcore.charging.operations.model.StatusBatchResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tingcore.cdc.charging.model.AggregatedChargePointTypeStatus.*;

public class ChargePointSiteMapper {

    public ChargePointSite toChargePointSite(ChargePointSiteWithAvailabilityRules chargePointSiteWithAvailabilityRules, StatusBatchResponse statusBatchResponse, Map<Long, Boolean> quickChargeMap) {

        CompleteChargePointSite ccps = chargePointSiteWithAvailabilityRules.getChargePointSite();

        // TODO remove ugly hack when operation service return maps
        Map<Long, ConnectorStatusResponse> connectorMap = new HashMap<>();

        return new ChargePointSite(
                ccps.getId(),
                ccps.getName(),
                ccps.getLocation(),
                "No description available", // TODO This should be part of CompleteChargeSite
                getStatusByType(chargePointSiteWithAvailabilityRules, statusBatchResponse, quickChargeMap, connectorMap),
                ccps.getChargePoints().stream().map(cp -> toChargePoint(cp, quickChargeMap, connectorMap)).collect(Collectors.toList()),
                null
        );

    }

    private List<ChargePointTypeStatus> getStatusByType(ChargePointSiteWithAvailabilityRules chargePointSiteWithAvailabilityRules, Map<Long, Boolean> quickChargeMap, Map<Long, ConnectorStatusResponse> connectorMap) {
        List<AvailabilityRulesWithChargePointId> availabilityRules = chargePointSiteWithAvailabilityRules.getAvailabilityRulesWithChargePointIds();
        CompleteChargePointSite chargePointSite = chargePointSiteWithAvailabilityRules.getChargePointSite();
        Map<com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum, StatusAccumelator> statusByChargePointType = new HashMap<>();
        Map<Long, List<ConnectorModelAvailabilityRule>> rulesByChargePointTypeId = new HashMap<>();

        availabilityRules.forEach(r -> {
            rulesByChargePointTypeId.put(r.getChargePointId(), r.getRules());
        });

        chargePointSite.getChargePoints().forEach(ccp -> {
            Long chargePointTypeId = ccp.getChargePointTypeId();
            List<ConnectorModelAvailabilityRule> connectorModelAvailabilityRules = rulesByChargePointTypeId.get(chargePointTypeId);
            if(connectorModelAvailabilityRules.isEmpty()) {
                ccp.getConnectors().forEach(con -> {
                    StatusAccumelator statusAccumelator = statusByChargePointType.computeIfAbsent(con.getConnectorType(),
                            conType -> new StatusAccumelator());
                    ConnectorStatus connectorStatus = getConnectorStatus(con, Optional.empty(), connectorMap, quickChargeMap);

                    switch (connectorStatus) {
                        case OUT_OF_ORDER_QUICKCHARGE:
                            statusAccumelator.incOutOfOrderQuickcharge();
                            break;
                        case OUT_OF_ORDER:
                            statusAccumelator.incOutOfOrder();
                            break;
                        case OCCUPIED_QUICKCHARGE:
                            statusAccumelator.incOccupiedQuickcharge();
                            break;
                        case OCCUPIED:
                            statusAccumelator.incOccupied();
                            break;
                        case RESERVED_QUICKCHARGE:
                            statusAccumelator.incReservedQuickcharge();
                            break;
                        case RESERVED:
                            statusAccumelator.incReserved();
                            break;
                        case AVAILABLE_QUICKCHARGE:
                            statusAccumelator.incAvailableQuickcharge();
                            break;
                        case AVAILABLE:
                            statusAccumelator.incAvailable();
                            break;
                    }

                });
            }


        });

        statusByChargePointType.entrySet().stream().map(e -> {
            com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum type = e.getKey();
            StatusAccumelator status = e.getValue();

            return new ChargePointTypeStatus(toAggregatedStatus(status),
                    status.getAvailable(),
                    status.getAvailableQuickcharge(),
                    status.getOccupied(),
                    status.getOccupiedQuickcharge(),
                    status.getReserved(),
                    status.getReservedQuickcharge(),
                    status.getOutOfOrder(),
                    status.getOutOfOrderQuickcharge());

        })


        return null;
    }

    private AggregatedChargePointTypeStatus toAggregatedStatus(StatusAccumelator status) {
        if(status.getAvailableQuickcharge() > 0) {
            return AggregatedChargePointTypeStatus.AVAILABLE_QUICKCHARGE;
        } else if(status.getAvailable() > 0) {
            return AggregatedChargePointTypeStatus.AVAILABLE;
        } else if (status.getOccupiedQuickcharge() > 0) {
            return AggregatedChargePointTypeStatus.OCCUPIED_QUICKCHARGE;
        } else if (status.getOccupied() > 0) {
            return 
        }
    }

    public ConnectorStatus getConnectorStatus(com.tingcore.charging.assets.model.Connector c, Optional<ConnectorModelAvailabilityRule> rule, Map<Long, ConnectorStatusResponse> connectorMap, Map<Long, Boolean> quickChargeMap) {
        // TODO Add availability rule check
        ConnectorStatusResponse connectorStatus = connectorMap.get(c.getId());
        if(quickChargeMap.get(c.getId())) {
            if(connectorStatus.getOutOfOrder()) {
                return ConnectorStatus.OUT_OF_ORDER_QUICKCHARGE;
            } else if(connectorStatus.getReserved()) {
                return ConnectorStatus.RESERVED_QUICKCHARGE;
            } else if (connectorStatus.getBusy()) {
                return ConnectorStatus.OCCUPIED_QUICKCHARGE;
            } else {
                return ConnectorStatus.AVAILABLE;
            }
        } else {
            if(connectorStatus.getOutOfOrder()) {
                return ConnectorStatus.OUT_OF_ORDER;
            } else if(connectorStatus.getReserved()) {
                return ConnectorStatus.RESERVED;
            } else if (connectorStatus.getBusy()) {
                return ConnectorStatus.OCCUPIED;
            } else {
                return ConnectorStatus.AVAILABLE;
            }
        }
    }

    public ChargePoint toChargePoint(CompleteChargePoint ccp, Map<Long, Boolean> quickChargeMap, Map<Long, ConnectorStatusResponse> connectorMap) {
        return new ChargePoint(
                ccp.getId(),
                ccp.getAssetName(),
                ccp.getConnectors().stream().map(c -> toConnector(c, quickChargeMap, connectorMap)).collect(Collectors.toList()));
    }

    public Connector toConnector(com.tingcore.charging.assets.model.Connector c, Map<Long, Boolean> quickChargeMap, Map<Long, ConnectorStatusResponse> connectorMap) {
        return new Connector(
                c.getId(),
                getLabel(c.getConnectorNumber()),
                c.getConnectorNumber(),
                c.getConnectorType(),
                quickChargeMap.get(c.getId()),
                toConnectorStatus(connectorMap.get(c.getId())),
                "No price available");
    }

    private ConnectorStatus toConnectorStatus(ConnectorStatusResponse connectorStatusResponse) {
        if(connectorStatusResponse.getOutOfOrder()) {
            return ConnectorStatus.OUT_OF_ORDER;
        } else if (connectorStatusResponse.getBusy()) {
            return ConnectorStatus.OCCUPIED;
        } else if (connectorStatusResponse.getReserved()) {
            return ConnectorStatus.RESERVED;
        } else {
            return ConnectorStatus.AVAILABLE;
        }
    }

    private String getLabel(int connectorNumber) {
        String[] connectorLabels = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T"};

        // ConnectorNumber 0 means all
        int labelIndex = connectorNumber - 1;
        if(labelIndex < 0 || labelIndex > connectorLabels.length) {
            return "NO LABEL PRESENT";
        }

        return connectorLabels[labelIndex];
    }


    private AggregatedChargePointTypeStatus getStatus(CompleteChargePointSite ccps, StatusBatchResponse statusBatchResponse, Map<Long, Boolean> quickChargeMap, Map<Long, ConnectorStatusResponse> connectorMap) {

        // TODO remove ugly hack when operation service return maps
        Map<Long, ChargePointStatusResponse> chargePointMap = new HashMap<>();

        int available = 0;
        int availableQuickcharge = 0;
        int occupied = 0;
        int occupiedQuickcharge = 0;
        int reserved = 0;
        int reservedQuickcharge = 0;
        int outOfOrder = 0;
        int outOfOrderQuickcharge = 0;

        for(ChargePointStatusResponse cpsr : statusBatchResponse.getStatusResponses()) {
            chargePointMap.put(cpsr.getChargePointId(), cpsr);
            for(ConnectorStatusResponse csr : cpsr.getConnectorStatusResponses()) {
                connectorMap.put(csr.getConnectorId(), csr);
            }
        }

        for(CompleteChargePoint ccp : ccps.getChargePoints()) {
            for(Connector c : ccp.getConnectors()) {
                ConnectorStatusResponse csr = connectorMap.get(c.getId());
                if(quickChargeMap.get(c.getId())) {
                    if(csr.getBusy()) {
                        occupiedQuickcharge++;
                    } else if (csr.getReserved()) {
                        reservedQuickcharge++;
                    } else if (csr.getOutOfOrder()) {
                        outOfOrderQuickcharge++;
                    } else {
                        availableQuickcharge++;
                    }
                } else {
                    if(csr.getBusy()) {
                        occupied++;
                    } else if (csr.getReserved()) {
                        reserved++;
                    } else if (csr.getOutOfOrder()) {
                        outOfOrder++;
                    } else {
                        available++;
                    }
                }
            }
        }

        if(availableQuickcharge > 0) {
            return AVAILABLE_QUICKCHARGE;
        }

        if(available > 0) {
            return AVAILABLE;
        }

        if(occupiedQuickcharge > 0) {
            return OCCUPIED_QUICKCHARGE;
        }

        if(occupied > 0) {
            return OCCUPIED;
        }

        return OUT_OF_ORDER;

    }

}

