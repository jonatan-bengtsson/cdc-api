package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.model.ChargePoint;
import com.tingcore.cdc.charging.model.ChargePointSite;
import com.tingcore.cdc.charging.model.Connector;
import com.tingcore.charging.assets.model.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ChargePointSiteMapper {

    public ChargePointSite toChargePointSite(CompleteChargePointSite ccps, Map<Long, ConnectorStatus> connectorStatusMap) {
        return new ChargePointSite(
                ccps.getId(),
                ccps.getName(),
                ccps.getLocation(),
                "No description available", // TODO This should be part of CompleteChargeSite
                getStatusByType(ccps, connectorStatusMap),
                ccps.getChargePoints().stream().map(cp -> toChargePoint(cp, connectorStatusMap)).collect(Collectors.toList()),
                null
        );
    }

    private List<ChargePointTypeStatus> getStatusByType(CompleteChargePointSite chargePointSite, Map<Long, ConnectorStatus> connectorStatusMap) {
        Map<com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum, StatusAccumelator> statusByChargePointType = new EnumMap<>(com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum.class);

        chargePointSite.getChargePoints().forEach(ccp -> {
            ccp.getConnectors().forEach(con -> {
                StatusAccumelator statusAccumelator = statusByChargePointType.computeIfAbsent(con.getConnectorType(),
                        conType -> new StatusAccumelator());

                updateStatusAccumelator(statusAccumelator, connectorStatusMap.get(con.getId()));
            });
        });

        return statusByChargePointType.entrySet().stream().map(e -> {
            com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum type = e.getKey();
            StatusAccumelator status = e.getValue();

            return new ChargePointTypeStatus(toAggregatedStatus(status),
                    status.getAvailable(),
                    status.getOccupied(),
                    status.getReserved(),
                    status.getOutOfOrder(),
                    isQuickCharger(type));
        }).collect(Collectors.toList());

    }

    private boolean isQuickCharger(com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum type) {
        return (type == com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum.CCS ||
                type == com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum.CHADEMO ||
                type == com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum.CHADEMO_AND_CCS);
    }


    private void updateStatusAccumelator(StatusAccumelator statusAccumelator, ConnectorStatus connectorStatus) {
        switch (connectorStatus) {
            case OUT_OF_ORDER:
                statusAccumelator.incOutOfOrder();
                break;
            case OCCUPIED:
                statusAccumelator.incOccupied();
                break;
            case RESERVED:
                statusAccumelator.incReserved();
                break;
            case AVAILABLE:
                statusAccumelator.incAvailable();
                break;
        }
    }

    private AggregatedChargePointTypeStatus toAggregatedStatus(StatusAccumelator status) {
        if(status.getAvailable() > 0) {
            return AggregatedChargePointTypeStatus.AVAILABLE;
        } else if (status.getOccupied() > 0) {
            return AggregatedChargePointTypeStatus.OCCUPIED;
        } else if(status.getReserved() > 0) {
            return AggregatedChargePointTypeStatus.OCCUPIED;
        } else {
            return AggregatedChargePointTypeStatus.OUT_OF_ORDER;
        }
    }


    public ChargePoint toChargePoint(CompleteChargePoint ccp, Map<Long, ConnectorStatus> connectorMap) {
        return new ChargePoint(
                ccp.getId(),
                ccp.getAssetName(),
                ccp.getConnectors().stream().map(c -> toConnector(c, connectorMap.get(c.getId()))).collect(Collectors.toList()));
    }

    public Connector toConnector(com.tingcore.charging.assets.model.Connector c, ConnectorStatus status) {
        return new Connector(
                c.getId(),
                getLabel(c.getConnectorNumber()),
                c.getConnectorNumber(),
                c.getConnectorType(),
                isQuickCharger(c.getConnectorType()),
                status,
                "No price available");
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


    public ChargeSiteStatuses getAggergatedSitesStatues(List<com.tingcore.charging.assets.model.Connector> connectors, Map<Long, ConnectorStatus> connectorStatusMap) {
        ChargeSiteStatus quickStatus = null;
        ChargeSiteStatus siteStatus = null;
        for(com.tingcore.charging.assets.model.Connector c : connectors) {
            ConnectorStatus connectorStatus = connectorStatusMap.get(c.getId());
            if(isQuickCharger(c.getConnectorType())) {
                quickStatus = getPrioritizedStatus(quickStatus, connectorStatus);
            } else {
                siteStatus = getPrioritizedStatus(siteStatus, connectorStatus);
            }

            if(quickStatus == ChargeSiteStatus.AVAILABLE && siteStatus == ChargeSiteStatus.AVAILABLE) {
                return new ChargeSiteStatuses(quickStatus, siteStatus);
            }
        }

        if(quickStatus == null) {
            quickStatus = ChargeSiteStatus.NONE;
        }

        if(siteStatus == null) {
            siteStatus = ChargeSiteStatus.NONE;
        }

        return new ChargeSiteStatuses(quickStatus, siteStatus);
    }

    private ChargeSiteStatus getPrioritizedStatus(ChargeSiteStatus status, ConnectorStatus connectorStatus) {
        switch (connectorStatus) {
            case AVAILABLE:
                status = ChargeSiteStatus.AVAILABLE;
                break;
            case RESERVED:
                break;
            case OCCUPIED:
                if(status == null || status == ChargeSiteStatus.OUT_OF_ORDER) {
                    status = ChargeSiteStatus.OCCUPIED;
                }
                break;
            case OUT_OF_ORDER:
                if(status == null) {
                    status = ChargeSiteStatus.OUT_OF_ORDER;
                }
                break;
        }
        return status;
    }
}

