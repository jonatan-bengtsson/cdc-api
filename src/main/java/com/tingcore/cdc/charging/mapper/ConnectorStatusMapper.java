package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.model.ConnectorStatus;
import com.tingcore.charging.assets.model.AvailabilityRulesWithChargePointId;
import com.tingcore.charging.assets.model.ChargePointSiteWithAvailabilityRules;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.charging.assets.model.ConnectorModelAvailabilityRule;
import com.tingcore.charging.operations.model.ChargePointStatusResponse;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ConnectorStatusMapper {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectorStatusMapper.class);

    public Map<Long, ConnectorStatus> getStatusMap(List<ChargePointSiteWithAvailabilityRules> sites, StatusBatchResponse statusBatchResponse) {

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

            availabilityRulesWithChargePointIds.forEach(
                    arwcpi -> rulesByChargePointTypeId.put(arwcpi.getChargePointId(), arwcpi.getRules())
            );

            chargePointSite.getChargePoints().forEach(ccp -> {
                Long chargePointTypeId = ccp.getChargePointTypeId();
                List<ConnectorModelAvailabilityRule> connectorModelAvailabilityRules = rulesByChargePointTypeId.getOrDefault(chargePointTypeId, new ArrayList<>());


                ccp.getConnectors().forEach(con -> {
                    ConnectorStatus connectorStatus = getConnectorStatus(
                            con,
                            connectorModelAvailabilityRules.stream().filter(r -> r.getChargePointTypeId().equals(chargePointTypeId) &&
                                    ruleContainsConnector(con, r)
                            ).findFirst(),
                            connectorStatusResponseMap,
                            chargePointStatusMap);

                    connectorStatusMap.put(con.getId(), connectorStatus);

                });
            });
        });

        return connectorStatusMap;
    }

    public ConnectorStatus getConnectorStatus(com.tingcore.charging.assets.model.Connector c, Optional<ConnectorModelAvailabilityRule> rule, Map<Long, ConnectorStatusResponse> connectorMap, Map<Long, ChargePointStatusResponse> chargePointStatusMap) {

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
            for(long id : availabilityRule.getConnectorModelIds()) {
                if(id == c.getConnectorModelId()) {
                    continue;
                } else {
                    ConnectorStatusResponse otherConnector = connectorMap.get(id);
                    if(otherConnector.getBusy()) {
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


    private boolean ruleContainsConnector(com.tingcore.charging.assets.model.Connector con, ConnectorModelAvailabilityRule r) {
        return r.getConnectorModelIds().stream().anyMatch(connectorModelId -> con.getConnectorModelId().equals(connectorModelId));
    }
}
