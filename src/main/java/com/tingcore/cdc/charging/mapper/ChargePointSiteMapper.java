package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.charging.assets.model.ChargePointEntity;
import com.tingcore.charging.assets.model.ChargePointSiteEntity;
import com.tingcore.charging.assets.model.CompleteChargePoint;
import com.tingcore.charging.assets.model.CompleteChargePointSite;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class ChargePointSiteMapper {

    /**
     * Maps an asset service complete charge point site and a map of connector statuses to an API friendly version of the ChargePointSite
     *
     * @param ccps                    site provided from the asset service as is
     * @param connectorStatusProvider a provider that must provide a status for all connectors present at the site
     * @param connectorPriceProvider  a provider that must provide price information for all connectors present at the site
     * @return an API friendly version of ChargePointSite
     */
    public static ChargePointSite toChargePointSite(CompleteChargePointSite ccps,
                                                    ConnectorStatusProvider connectorStatusProvider,
                                                    ConnectorPriceProvider connectorPriceProvider) {
        ChargePointSiteEntity chargePointSiteEntity = ccps.getChargePointSiteEntity();
        long chargePointSiteId = chargePointSiteEntity.getMetadata().getId();
        String chargePointSiteName = chargePointSiteEntity.getData().getName();
        return new ChargePointSite(
                chargePointSiteId,
                chargePointSiteName,
                ccps.getLocationEntity().getData(),
                "No description available", // TODO This should be part of CompleteChargeSite
                getStatusByType(ccps, connectorStatusProvider),
                ccps.getChargePoints().stream().map(cp -> toChargePoint(cp, connectorStatusProvider, connectorPriceProvider)).collect(Collectors.toList()),
                null
        );
    }

    /**
     * Aggregates statuses on a site by connector type, for example one status for CSS, one status for CHADEMO etc
     *
     * @param chargePointSite         site provided from the asset service as is
     * @param connectorStatusProvider a provider that must provide a status for all connectors present at the site
     * @return a list which has aggregated the status on connector type
     */
    private static List<ChargePointTypeStatus> getStatusByType(CompleteChargePointSite chargePointSite,
                                                               ConnectorStatusProvider connectorStatusProvider) {
        Map<com.tingcore.charging.assets.model.BasicConnector.ConnectorTypeEnum, StatusAccumulator> statusByChargePointType = new EnumMap<>(com.tingcore.charging.assets.model.BasicConnector.ConnectorTypeEnum.class);

        chargePointSite.getChargePoints().forEach(ccp -> {
            ccp.getConnectorEntities().forEach(con -> {
                StatusAccumulator statusAccumulator = statusByChargePointType.computeIfAbsent(con.getData().getBasicConnector().getConnectorType(),
                        conType -> new StatusAccumulator());

                updateStatusAccumulator(statusAccumulator, con, ofNullable(connectorStatusProvider.statusFor(con.getMetadata().getId())).orElse(ConnectorStatus.NO_DATA));
            });
        });

        return statusByChargePointType.entrySet().stream().map(e -> {
            com.tingcore.charging.assets.model.BasicConnector.ConnectorTypeEnum type = e.getKey();
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
     *
     * @param c an asset service connector
     * @return true if the connector can supply power withing range 40kW to 50kW
     */
    private static boolean isQuickCharger(com.tingcore.charging.assets.model.ConnectorCapability c) {
        return c.getPower() >= 40_000 && c.getPower() <= 50_000;
    }

    /**
     * Convenience method for updating StatusAccumulator
     */
    private static void updateStatusAccumulator(StatusAccumulator statusAccumulator, com.tingcore.charging.assets.model.ConnectorEntity con, ConnectorStatus connectorStatus) {
        boolean quickCharge = isQuickCharger(con.getData().getConnectorCapability());
        switch (connectorStatus) {
            case OUT_OF_ORDER:
                if (quickCharge) {
                    statusAccumulator.incOutOfOrderQuickcharge();
                } else {

                    statusAccumulator.incOutOfOrder();
                }
                break;
            case OCCUPIED:
                if (quickCharge) {
                    statusAccumulator.incOccupiedQuickcharge();
                } else {

                    statusAccumulator.incOccupied();
                }
                break;
            case RESERVED:
                if (quickCharge) {
                    statusAccumulator.incReservedQuickcharge();
                } else {

                    statusAccumulator.incReserved();
                }
                break;
            case AVAILABLE:
                if (quickCharge) {
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
        if (status.getAvailable() > 0) {
            return AggregatedChargePointTypeStatus.AVAILABLE;
        } else if (status.getOccupied() > 0) {
            return AggregatedChargePointTypeStatus.OCCUPIED;
        } else if (status.getReserved() > 0) {
            return AggregatedChargePointTypeStatus.OCCUPIED;
        } else {
            return AggregatedChargePointTypeStatus.OUT_OF_ORDER;
        }
    }


    /**
     * Maps an Asset service CompleteChargePoint to an API friendly ChargePoint with connectors including status
     *
     * @param ccp                     CompleteChargePoint from Asset service
     * @param connectorStatusProvider a provider that must provide a status for all connectors present at the site
     * @param connectorPriceProvider  a provider that must provide price information for all connectors present at the site
     * @return An API friendly version of the ChargePoint
     */
    public static ChargePoint toChargePoint(CompleteChargePoint ccp,
                                            ConnectorStatusProvider connectorStatusProvider,
                                            ConnectorPriceProvider connectorPriceProvider) {
        ChargePointEntity cpe = ccp.getChargePointEntity();
        return new ChargePoint(
                cpe.getMetadata().getId(),
                cpe.getData().getBasicChargePoint().getAssetName(),
                ccp.getConnectorEntities().stream()
                        .map(c -> toConnector(c, connectorStatusProvider.statusFor(c.getMetadata().getId()), connectorPriceProvider.priceFor(c.getMetadata().getId())))
                        .collect(Collectors.toList()));
    }

    /**
     * Maps an Asset service Connector to an API friendly Connector with connectorStatus included
     *
     * @param c               Connector from Asset service
     * @param connectorStatus connectorStatus for the connector
     * @param connectorPrice  price information for the connector
     * @return An API friendly version of the Connector
     */
    public static Connector toConnector(com.tingcore.charging.assets.model.ConnectorEntity c,
                                        ConnectorStatus connectorStatus,
                                        ConnectorPrice connectorPrice) {
        int connectorNumber = c.getData().getBasicConnector().getConnectorNumber();
        return new Connector(
                c.getMetadata().getId(),
                getLabel(connectorNumber),
                connectorNumber,
                c.getData().getBasicConnector().getConnectorType(),
                isQuickCharger(c.getData().getConnectorCapability()),
                ofNullable(connectorStatus).orElse(ConnectorStatus.NO_DATA),
                ofNullable(connectorPrice).map(price -> price.price).orElse("no price information available"));
    }


    /**
     * Maps connector number to label, 1 => A, 2 => B and so on
     *
     * @param connectorNumber the connector number to map
     * @return the label for the connector
     */
    private static String getLabel(int connectorNumber) {
        String[] connectorLabels = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T"};

        // ConnectorNumber 0 means all
        int labelIndex = connectorNumber - 1;
        if (labelIndex < 0 || labelIndex > connectorLabels.length) {
            return "NO LABEL PRESENT";
        }

        return connectorLabels[labelIndex];
    }


    /**
     * Creates an aggregated ChargePointSite status from connectors and connectors status map
     * aggregation is split up on two versions quick chargers and non-quick chargers where AVAILABLE status takes priority
     * if no status can be aggregated for a version then status NONE is returned.
     *
     * @param connectors         the connectors present at the site to aggregate status for
     * @param connectorStatusMap a map that must contain a status for all connectors present at the site
     * @return an aggregated ChargePointSiteStatus object containing status for quick chargers and non-quick chargers
     */
    public static ChargePointSiteStatuses getAggregatedSitesStatues(List<com.tingcore.charging.assets.model.ConnectorEntity> connectors, Map<Long, ConnectorStatus> connectorStatusMap) {
        ChargeSiteStatus quickStatus = null;
        ChargeSiteStatus siteStatus = null;
        for (com.tingcore.charging.assets.model.ConnectorEntity c : connectors) {
            ConnectorStatus connectorStatus = connectorStatusMap.get(c.getMetadata().getId());
            if (isQuickCharger(c.getData().getConnectorCapability())) {
                quickStatus = getPrioritizedStatus(quickStatus, connectorStatus);
            } else {
                siteStatus = getPrioritizedStatus(siteStatus, connectorStatus);
            }

            if (quickStatus == ChargeSiteStatus.AVAILABLE && siteStatus == ChargeSiteStatus.AVAILABLE) {
                return new ChargePointSiteStatuses(quickStatus, siteStatus);
            }
        }

        if (quickStatus == null) {
            quickStatus = ChargeSiteStatus.NONE;
        }

        if (siteStatus == null) {
            siteStatus = ChargeSiteStatus.NONE;
        }

        return new ChargePointSiteStatuses(quickStatus, siteStatus);
    }

    /**
     * Takes a charge site status and a connector status and prioritizes and returns the charge site status possibly
     * updated.
     * <p>
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
                if (status == null || status == ChargeSiteStatus.OUT_OF_ORDER) {
                    status = ChargeSiteStatus.OCCUPIED;
                }
                break;
            case OUT_OF_ORDER:
                if (status == null) {
                    status = ChargeSiteStatus.OUT_OF_ORDER;
                }
                break;
        }
        return status;
    }

    public interface ConnectorStatusProvider {
        ConnectorStatus statusFor(final Long connectorId);
    }

    public interface ConnectorPriceProvider {
        ConnectorPrice priceFor(final Long connectorId);
    }
}

