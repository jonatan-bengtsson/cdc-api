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

            Map<Long, List<ConnectorModelAvailabilityRule>> rulesByChargePointTypeId = new HashMap<>();
            Map<Long, Long> connectorModelToConnector = new HashMap<>();

            availabilityRulesWithChargePointIds.forEach(
                    arwcpi -> rulesByChargePointTypeId.put(arwcpi.getChargePointId(), arwcpi.getRules())
            );

            chargePointSite.getChargePoints().forEach(ccp -> {
                Long chargePointTypeId = ccp.getChargePointTypeId();
                List<ConnectorModelAvailabilityRule> connectorModelAvailabilityRules = rulesByChargePointTypeId.getOrDefault(chargePointTypeId, new ArrayList<>());

                // Prevent unnecessary traversing if they're not going to be needed,
                // connectorModelToConnector map is only required to be populated if there is any rules to consider
                if(!connectorModelAvailabilityRules.isEmpty()) {
                    ccp.getConnectors().forEach(con -> connectorModelToConnector.put(con.getConnectorModelId(), con.getId()));
                }

                ccp.getConnectors().forEach(con -> {
                    ConnectorStatus connectorStatus = calculateConnectorStatus(
                            con,
                            ccp,
                            connectorModelAvailabilityRules.stream().filter(r -> r.getChargePointTypeId().equals(chargePointTypeId) &&
                                    ruleContainsConnector(con, r)
                            ).findFirst(),
                            connectorStatusResponseMap,
                            chargePointStatusMap,
                            connectorModelToConnector);

                    connectorStatusMap.put(con.getId(), connectorStatus);

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
     * @param connectorModelToConnector a map mapping connector model ids to connector ids
     * @return
     */
    public static ConnectorStatus calculateConnectorStatus(Connector c, CompleteChargePoint ccp, Optional<ConnectorModelAvailabilityRule> rule, Map<Long, ConnectorStatusResponse> connectorMap, Map<Long, ChargePointStatusResponse> chargePointStatusMap, Map<Long, Long> connectorModelToConnector) {

        if(ccp.getOperationalStatus() != CompleteChargePoint.OperationalStatusEnum.IN_OPERATION) {
            return ConnectorStatus.OUT_OF_ORDER;
        }

        if(c.getOperationalStatus() == com.tingcore.charging.assets.model.Connector.OperationalStatusEnum.OUT_OF_ORDER) {
            return ConnectorStatus.OUT_OF_ORDER;
        }

        if(!chargePointStatusMap.get(c.getChargePointId()).getOnline()) {
            return ConnectorStatus.OUT_OF_ORDER;
        }

        ConnectorStatusResponse connectorStatus = connectorMap.get(c.getId());

        // Connectors covered by the same rule shared busy/occupied status
        if(rule.isPresent()) {
            ConnectorModelAvailabilityRule availabilityRule = rule.get();
            for(long modelId : availabilityRule.getConnectorModelIds()) {
                if(modelId == c.getConnectorModelId()) {
                    continue;
                } else {
                    ConnectorStatusResponse otherConnector = connectorMap.get(connectorModelToConnector.get(modelId));
                    if(otherConnector.getReserved()) {
                        return ConnectorStatus.RESERVED;
                    }
                    if(otherConnector.getBusy()) {
                        return ConnectorStatus.OCCUPIED;
                    }
                }
            }
        }

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


    private static boolean ruleContainsConnector(com.tingcore.charging.assets.model.Connector con, ConnectorModelAvailabilityRule r) {
        return r.getConnectorModelIds().stream().anyMatch(connectorModelId -> con.getConnectorModelId().equals(connectorModelId));
    }
}
