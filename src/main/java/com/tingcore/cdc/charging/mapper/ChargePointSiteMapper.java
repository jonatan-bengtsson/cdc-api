package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.model.ChargePoint;
import com.tingcore.cdc.charging.model.ChargePointSite;
import com.tingcore.cdc.charging.model.Connector;
import com.tingcore.charging.assets.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class ChargePointSiteMapper {

    /**
     *  Maps an asset service complete charge point site and a map of connector statuses to an API friendly version of the ChargePointSite
     * @param ccps site provided from the asset service as is
     * @param connectorStatusMap a map that must contain a status for all connectors present at the site
     * @return an API friendly version of ChargePointSite
     */
    public static ChargePointSite toChargePointSite(CompleteChargePointSite ccps, Map<Long, ConnectorStatus> connectorStatusMap) {
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

    /**
     * Aggregates statuses on a site by connector type, for example one status for CSS, one status for CHADEMO etc
     * @param chargePointSite site provided from the asset service as is
     * @param connectorStatusMap a map that must contain a status for all connectors present at the site
     * @return a list which has aggreagated the status on connector type
     */
    private static List<ChargePointTypeStatus> getStatusByType(CompleteChargePointSite chargePointSite, Map<Long, ConnectorStatus> connectorStatusMap) {
        Map<com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum, StatusAccumulator> statusByChargePointType = new EnumMap<>(com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum.class);

        chargePointSite.getChargePoints().forEach(ccp -> {
            ccp.getConnectors().forEach(con -> {
                StatusAccumulator statusAccumulator = statusByChargePointType.computeIfAbsent(con.getConnectorType(),
                        conType -> new StatusAccumulator());

                updateStatusAccumulator(statusAccumulator, con, connectorStatusMap.get(con.getId()));
            });
        });

        return statusByChargePointType.entrySet().stream().map(e -> {
            com.tingcore.charging.assets.model.Connector.ConnectorTypeEnum type = e.getKey();
            StatusAccumulator status = e.getValue();

            return new ChargePointTypeStatus(toAggregatedStatus(status),
                    type,
                    status.getAvailable(),
                    status.getOccupied(),
                    status.getReserved(),
                    status.getOutOfOrder(),
                    status.getAvailableQuickcharge(),
                    status.getOccupiedQuickcharge(),
                    status.getReservedQuickcharge(),
                    status.getOutOfOrderQuickcharge());
        }).collect(Collectors.toList());

    }

    /**
     * Quick charger is defined as having a connector with power withing range 40kW - 50kW
     * 50kW > is considered a High Power Charger
     * @param c an asset service connector
     * @return true if the connector can supply power withing range 40kW to 50kW
     */
    private static boolean isQuickCharger(com.tingcore.charging.assets.model.Connector c) {
        return c.getPower() >= 40_000 && c.getPower() <= 50_000;
    }

    /**
     * Convenience method for updating StatusAccumulator
     */
    private static void updateStatusAccumulator(StatusAccumulator statusAccumulator, com.tingcore.charging.assets.model.Connector con, ConnectorStatus connectorStatus) {
        boolean quickCharge = isQuickCharger(con);
        switch (connectorStatus) {
            case OUT_OF_ORDER:
                if(quickCharge) {
                    statusAccumulator.incOutOfOrderQuickcharge();
                } else {

                    statusAccumulator.incOutOfOrder();
                }
                break;
            case OCCUPIED:
                if(quickCharge) {
                    statusAccumulator.incOccupiedQuickcharge();
                } else {

                    statusAccumulator.incOccupied();
                }
                break;
            case RESERVED:
                if(quickCharge) {
                    statusAccumulator.incReservedQuickcharge();
                } else {

                    statusAccumulator.incReserved();
                }
                break;
            case AVAILABLE:
                if(quickCharge) {
                    statusAccumulator.incAvailableQuickcharge();
                } else {

                    statusAccumulator.incAvailable();
                }
                break;
        }
    }

    /**
     * Convenience method to aggregate status from an accumulator, status is prioritized in the following order:
     * AVAILABLE, OCCUPIED, OUT_OF_ORDER, where earlier statuses takes precedence
     */
    private static AggregatedChargePointTypeStatus toAggregatedStatus(StatusAccumulator status) {
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


    /**
     * Maps an Asset service CompleteChargePoint to an API friendly ChargePoint with connectors including status
     * @param ccp CompleteChargePoint from Asset service
     * @param connectorMap a map that must contain a status for all connectors present at the charge point
     * @return An API friendly version of the ChargePoint
     */
    public static ChargePoint toChargePoint(CompleteChargePoint ccp, Map<Long, ConnectorStatus> connectorMap) {
        return new ChargePoint(
                ccp.getId(),
                ccp.getAssetName(),
                ccp.getConnectors().stream().map(c -> toConnector(c, connectorMap.get(c.getId()))).collect(Collectors.toList()));
    }

    /**
     * Maps an Asset service Connector to an API friendly Connector with status included
     * @param c Connector from Asset service
     * @param status Status for the connector
     * @return An API friendly version of the Connector
     */
    public static Connector toConnector(com.tingcore.charging.assets.model.Connector c, ConnectorStatus status) {
        return new Connector(
                c.getId(),
                getLabel(c.getConnectorNumber()),
                c.getConnectorNumber(),
                c.getConnectorType(),
                isQuickCharger(c),
                status,
                "No price available");
    }


    /**
     * Maps connector number to label, 1 => A, 2 => B and so on
     * @param connectorNumber the connector number to map
     * @return the label for the connector
     */
    private static String getLabel(int connectorNumber) {
        String[] connectorLabels = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T"};

        // ConnectorNumber 0 means all
        int labelIndex = connectorNumber - 1;
        if(labelIndex < 0 || labelIndex > connectorLabels.length) {
            return "NO LABEL PRESENT";
        }

        return connectorLabels[labelIndex];
    }


    /**
     * Creates an aggregated ChargePointSite status from connectors and connectors status map
     * aggregation is split up on two versions quick chargers and non-quick chargers where AVAILABLE status takes priority
     * if no status can be aggregated for a version then status NONE is returned.
     * @param connectors the connectors present at the site to aggregate status for
     * @param connectorStatusMap a map that must contain a status for all connectors present at the site
     * @return an aggregated ChargePointSiteStatus object containing status for quick chargers and non-quick chargers
     */
    public static ChargePointSiteStatuses getAggregatedSitesStatues(List<com.tingcore.charging.assets.model.Connector> connectors, Map<Long, ConnectorStatus> connectorStatusMap) {
        ChargeSiteStatus quickStatus = null;
        ChargeSiteStatus siteStatus = null;
        for(com.tingcore.charging.assets.model.Connector c : connectors) {
            ConnectorStatus connectorStatus = connectorStatusMap.get(c.getId());
            if(isQuickCharger(c)) {
                quickStatus = getPrioritizedStatus(quickStatus, connectorStatus);
            } else {
                siteStatus = getPrioritizedStatus(siteStatus, connectorStatus);
            }

            if(quickStatus == ChargeSiteStatus.AVAILABLE && siteStatus == ChargeSiteStatus.AVAILABLE) {
                return new ChargePointSiteStatuses(quickStatus, siteStatus);
            }
        }

        if(quickStatus == null) {
            quickStatus = ChargeSiteStatus.NONE;
        }

        if(siteStatus == null) {
            siteStatus = ChargeSiteStatus.NONE;
        }

        return new ChargePointSiteStatuses(quickStatus, siteStatus);
    }

    /**
     * Takes a charge site status and a connector status and prioritizes and returns the charge site status possibly
     * updated.
     *
     * Statuses are prioritized in the following order AVAILABLE, OCCUPIED, OUT_OF_ORDER
     *
     * @param status
     * @param connectorStatus
     * @return
     */
    private static ChargeSiteStatus getPrioritizedStatus(ChargeSiteStatus status, ConnectorStatus connectorStatus) {
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

