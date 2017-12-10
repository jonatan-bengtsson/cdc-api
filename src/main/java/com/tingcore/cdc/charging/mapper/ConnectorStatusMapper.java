package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.model.ConnectorStatus;
import com.tingcore.charging.assets.model.*;
import com.tingcore.charging.operations.model.ChargePointStatusResponse;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ConnectorStatusMapper {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectorStatusMapper.class);

    /**
     * Given a list of sites with availability rule from the asset service and a status batch response from the operations service
     * a map is created with mapping connector id with a status
     * @param sites list of ChargePointSiteWithAvailabilityRules
     * @param statusBatchResponse a status batch response that must contain statuses for each charge point and connector part of the list of sites provided
     * @return a status map mapping connector id to respective status
     */
    public static Map<Long, ConnectorStatus> getStatusMap(List<ChargePointSiteWithAvailabilityRules> sites, StatusBatchResponse statusBatchResponse) {

        Map<Long, ConnectorStatusResponse> connectorStatusResponseMap = new HashMap<>();
        Map<Long, ChargePointStatusResponse> chargePointStatusMap = new HashMap<>();
        Map<Long, ConnectorStatus> connectorStatusMap = new HashMap<>();

        statusBatchResponse.getStatusResponses().forEach(sr -> {
            chargePointStatusMap.put(sr.getChargePointId(), sr);
            sr.getConnectorStatusResponses().forEach(cs -> connectorStatusResponseMap.put(cs.getConnectorId(), cs));
        });

        sites.forEach(cpswa -> {
            CompleteChargePointSite chargePointSite = cpswa.getChargePointSite();
            List<AvailabilityRulesWithChargePointId> availabilityRulesWithChargePointIds = cpswa.getAvailabilityRulesWithChargePointIds();

            Map<Long, List<ConnectorModelAvailabilityRuleEntity>> rulesByChargePointTypeId = new HashMap<>();


            availabilityRulesWithChargePointIds.forEach(
                    arwcpi -> rulesByChargePointTypeId.put(arwcpi.getChargePointId(), arwcpi.getRules())
            );

            chargePointSite.getChargePoints().forEach(ccp -> {
                // Within a charge point there is a s-s mapping fronm connector number to connector
                Map<Integer, Long> connectorNumberToConnector = new HashMap<>();
                ChargePointEntity cpe = ccp.getChargePointEntity();
                Long chargePointModelId = cpe.getData().getBasicChargePoint().getChargePointModelId();
                List<ConnectorModelAvailabilityRuleEntity> connectorModelAvailabilityRules = rulesByChargePointTypeId.getOrDefault(chargePointModelId, new ArrayList<>());

                // Prevent unnecessary traversing if they're not going to be needed,
                // connectorModelToConnector map is only required to be populated if there is any rules to consider
                if(!connectorModelAvailabilityRules.isEmpty()) {
                    ccp.getConnectorEntities().forEach(con -> connectorNumberToConnector.put(con.getData().getBasicConnector().getConnectorNumber(), con.getMetadata().getId()));
                }

                ccp.getConnectorEntities().forEach(con -> {
                    ConnectorStatus connectorStatus = calculateConnectorStatus(
                            con,
                            ccp,
                            connectorModelAvailabilityRules.stream().filter(r -> r.getData().getChargePointModelId().equals(chargePointModelId) &&
                                    ruleContainsConnector(con, r)
                            ).map(r-> r.getData()).findFirst(),
                            connectorStatusResponseMap,
                            chargePointStatusMap,
                            connectorNumberToConnector);

                    connectorStatusMap.put(con.getMetadata().getId(), connectorStatus);

                });
            });
        });

        return connectorStatusMap;
    }

    /**
     * Calculates the connector status for a given connector merging information from asset service and operation service
     * including current status, availability rules and operational state.
     *
     * If the operational status provided by asset service on the charge point is set to anything else than IN_OPERATION
     *  then status is OUT_OF_ORDER
     *
     * If the operational status provided by asset service on the connector is set to OUT_OF_ORDER
     *  then status is OUT_OF_ORDER
     *
     * If the charge point is not online
     *  then the status is OUT_OF_ORDER
     *
     * If there is a rule present for the connector in question than it should share occupied status with any other
     * connectors covered by the rule. A rule with connectors [1,2,3] and if any one of them is occupied then all should have the
     * status OCCUPIED
     *
     * If no rule is present or none of the other connectors coverd by the rule then the status from operations service determines
     * the status of the connector
     *
     *
     * @param c the connector to calculate status for
     * @param ccp the charge point having the connector
     * @param rule optional availability rule, if any rule involves the connector in question
     *             Rule determines if a connector should share occupied status
     *             if both connector 1 and 2 are included in the rule only one of them can be charged at simultaneously
     * @param connectorMap a map that must contain any connectors included in the rule provided and the connector c in question
     *                     mapping their respective status from operations service
     * @param chargePointStatusMap a map that must contain the charge point the connector in question is present on
     *                             mapping it's specific status from the operations service
     * @param connectorNumberToConnector a map mapping connector number to connector ids
     * @return
     */
    public static ConnectorStatus calculateConnectorStatus(ConnectorEntity c, CompleteChargePoint ccp, Optional<ConnectorModelAvailabilityRule> rule, Map<Long, ConnectorStatusResponse> connectorMap, Map<Long, ChargePointStatusResponse> chargePointStatusMap, Map<Integer, Long> connectorNumberToConnector) {

        if(ccp.getChargePointEntity().getData().getBasicChargePoint().getOperationalStatus() != BasicChargePoint.OperationalStatusEnum.IN_OPERATION) {
            return ConnectorStatus.OUT_OF_ORDER;
        }

        if(c.getData().getBasicConnector().getOperationalStatus() == com.tingcore.charging.assets.model.BasicConnector.OperationalStatusEnum.OUT_OF_ORDER) {
            return ConnectorStatus.OUT_OF_ORDER;
        }

        if(!chargePointStatusMap.get(c.getData().getBasicConnector().getChargePointId()).isOnline()) {
            return ConnectorStatus.OUT_OF_ORDER;
        }

        ConnectorStatusResponse connectorStatus = connectorMap.get(c.getMetadata().getId());

        // Connectors covered by the same rule shared busy/occupied status
        if(rule.isPresent()) {
            ConnectorModelAvailabilityRule availabilityRule = rule.get();
            for(int connectorNumber : availabilityRule.getConnectorNumbers()) {
                if(connectorNumber == c.getData().getBasicConnector().getConnectorNumber()) {
                    continue;
                } else {
                    ConnectorStatusResponse otherConnector = connectorMap.get(connectorNumberToConnector.get(connectorNumber));
                    if(otherConnector.isReserved()) {
                        return ConnectorStatus.RESERVED;
                    }
                    if(otherConnector.isBusy()) {
                        return ConnectorStatus.OCCUPIED;
                    }
                }
            }
        }

        if(connectorStatus.isOutOfOrder()) {
            return ConnectorStatus.OUT_OF_ORDER;
        } else if(connectorStatus.isReserved()) {
            return ConnectorStatus.RESERVED;
        } else if (connectorStatus.isBusy()) {
            return ConnectorStatus.OCCUPIED;
        } else {
            return ConnectorStatus.AVAILABLE;
        }
    }


    private static boolean ruleContainsConnector(com.tingcore.charging.assets.model.ConnectorEntity con, ConnectorModelAvailabilityRuleEntity r) {
        BasicConnector basicConnector = con.getData().getBasicConnector();
        return r.getData().getConnectorNumbers().stream().anyMatch(connectorNumber -> basicConnector.getConnectorNumber().equals(connectorNumber));
    }
}
