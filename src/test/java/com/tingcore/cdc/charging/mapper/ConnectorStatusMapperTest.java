package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.mapper.mock.ChargePointFactory;
import com.tingcore.cdc.charging.model.ConnectorStatus;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.charging.assets.model.*;
import com.tingcore.charging.operations.model.ChargePointStatusResponse;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.tingcore.cdc.charging.mapper.mock.ChargePointFactory.createBasicChargePointStatus;
import static com.tingcore.cdc.charging.mapper.mock.ChargePointFactory.createChargePointStatus;
import static com.tingcore.cdc.charging.mapper.mock.ChargePointFactory.createCompleteChargePoint;
import static com.tingcore.cdc.charging.mapper.mock.ConnectorFactory.createConnector;
import static com.tingcore.cdc.charging.mapper.mock.ConnectorFactory.createConnectorStatus;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Import({ConnectorStatusMapper.class})
@ActiveProfiles(SpringProfilesConstant.UNIT_TEST)
public class ConnectorStatusMapperTest {

    @Autowired
    private ConnectorStatusMapper connectorStatusMapper;


    @Test
    public void getStatusMap() throws Exception {

        Long chargePointTypeId = 5L;
        Long chargePointId = 1L;

        Long connector1Id = 20L;
        Long connector2Id = 30L;

        Long connector3Id = 40L;
        Long connector4Id = 50L;

        Long connector5Id = 60L;
        Long connector6Id = 70L;

        Long connectorModel1Id = 1L;
        Long connectorModel2Id = 2L;

        Long connectorModel3Id = 3L;
        Long connectorModel4Id = 4L;
        Long connectorModel5Id = 5L;
        Long connectorModel6Id = 6L;

        Long chargePoint2Id = 2L;
        Long chargePoint3Id = 3L;

        List<ChargePointSiteWithAvailabilityRules> sites = Collections.singletonList(
                new ChargePointSiteWithAvailabilityRules()
                        .addAvailabilityRulesWithChargePointIdsItem(
                                createAvailabilityRule(chargePointTypeId, Arrays.asList(connectorModel1Id, connectorModel2Id))
                        )
                        .chargePointSite(
                                new CompleteChargePointSite()
                                        .id(10L)
                                        .chargePoints(Arrays.asList(
                                                new CompleteChargePoint()
                                                        .id(chargePointId)
                                                        .operationalStatus(CompleteChargePoint.OperationalStatusEnum.IN_OPERATION)
                                                        .chargePointTypeId(chargePointTypeId)
                                                        .connectors(
                                                                Arrays.asList(
                                                                        createConnector(chargePointId, connector1Id, connectorModel1Id),
                                                                        createConnector(chargePointId, connector2Id, connectorModel2Id)
                                                                )
                                                        ),
                                                new CompleteChargePoint()
                                                        .id(chargePoint2Id)
                                                        .operationalStatus(CompleteChargePoint.OperationalStatusEnum.IN_OPERATION)
                                                        .chargePointTypeId(chargePointTypeId)
                                                        .connectors(
                                                                Arrays.asList(
                                                                        createConnector(chargePoint2Id, connector3Id, connectorModel3Id),
                                                                        createConnector(chargePoint2Id, connector4Id, connectorModel4Id)
                                                                )
                                                        ),
                                                new CompleteChargePoint()
                                                        .id(chargePoint3Id)
                                                        .operationalStatus(CompleteChargePoint.OperationalStatusEnum.IN_OPERATION)
                                                        .chargePointTypeId(chargePointTypeId)
                                                        .connectors(
                                                                Arrays.asList(
                                                                        createConnector(chargePoint3Id, connector5Id, connectorModel5Id),
                                                                        createConnector(chargePoint3Id, connector6Id, connectorModel6Id)
                                                                )
                                                        )
                                                )
                                        )
                        )
        );


        StatusBatchResponse statusBatchResponse = new StatusBatchResponse()
                .statusResponses(Arrays.asList(
                        createChargePointStatus(chargePointId, true,
                                Arrays.asList(
                                        createConnectorStatus(connector1Id, true, false, false),
                                        createConnectorStatus(connector2Id, false, false, false)
                                )
                        ),
                        createChargePointStatus(chargePoint2Id, true,
                                Arrays.asList(
                                        createConnectorStatus(connector3Id, true, false, false),
                                        createConnectorStatus(connector4Id, false, false, false)
                                )
                        ),
                        createChargePointStatus(chargePoint3Id, false,
                                Arrays.asList(
                                        createConnectorStatus(connector5Id, true, false, false),
                                        createConnectorStatus(connector6Id, false, false, false)
                                )
                        )
                ));


        Map<Long, ConnectorStatus> statusMap = connectorStatusMapper.getStatusMap(sites, statusBatchResponse);

        assertEquals(ConnectorStatus.OCCUPIED, statusMap.get(connector1Id));
        assertEquals(ConnectorStatus.OCCUPIED, statusMap.get(connector2Id));
        assertEquals(ConnectorStatus.OCCUPIED, statusMap.get(connector3Id));
        assertEquals(ConnectorStatus.AVAILABLE, statusMap.get(connector4Id));
        assertEquals(ConnectorStatus.OUT_OF_ORDER, statusMap.get(connector5Id));
        assertEquals(ConnectorStatus.OUT_OF_ORDER, statusMap.get(connector6Id));

    }

    private AvailabilityRulesWithChargePointId createAvailabilityRule(Long chargePointTypeId, List<Long> connectorModelIds) {
        return new AvailabilityRulesWithChargePointId()
                .chargePointId(chargePointTypeId)
                .rules(
                        Collections.singletonList(
                                new ConnectorModelAvailabilityRule()
                                        .chargePointTypeId(chargePointTypeId)
                                        .connectorModelIds(connectorModelIds)
                        )
                );
    }

    @Test
    public void getConnectorStatusAvailabilityRuleTest() throws Exception {
        // Verifies if 1 out of 2 connectors in the same group is busy the other one is considered OCCUPIED as well
        Long chargePointId = 1L;
        Long connector1Id = 123L;
        Long connector2Id = 234L;
        Long connectorModel1Id = 5L;
        Long connectorModel2Id = 15L;
        Long chargePointTypeId = 3L;

        Connector con1 = createConnector(chargePointId, connector1Id, connectorModel1Id);
        Connector con2 = createConnector(chargePointId, connector2Id, connectorModel2Id);
        CompleteChargePoint cp = createCompleteChargePoint(chargePointId, chargePointTypeId, Arrays.asList(con1, con2), CompleteChargePoint.OperationalStatusEnum.IN_OPERATION);

        Optional<ConnectorModelAvailabilityRule> rule = Optional.of(
                new ConnectorModelAvailabilityRule()
                        .chargePointTypeId(chargePointTypeId)
                        .connectorModelIds(Arrays.asList(connectorModel1Id, connectorModel2Id))
        );

        Map<Long, ConnectorStatusResponse> conMap = new HashMap<>();
        conMap.put(connector1Id, createConnectorStatus(connector1Id, true, false, false));
        conMap.put(connector2Id, createConnectorStatus(connector2Id, false, false, false));


        Map<Long, ChargePointStatusResponse> cpMap = new HashMap<>();
        cpMap.put(chargePointId, createBasicChargePointStatus(chargePointId, true));

        Map<Long, Long> connectorModelToConnector = new HashMap<>();
        connectorModelToConnector.put(connectorModel1Id, connector1Id);
        connectorModelToConnector.put(connectorModel2Id, connector2Id);

        assertEquals(ConnectorStatus.OCCUPIED, connectorStatusMapper.getConnectorStatus(con1, cp, rule, conMap, cpMap, connectorModelToConnector));
        assertEquals(ConnectorStatus.OCCUPIED, connectorStatusMapper.getConnectorStatus(con2, cp, rule, conMap, cpMap, connectorModelToConnector));
    }

    @Test
    public void getConnectorStatusAvailabilityChargePointOfflineTest() throws Exception {
        // Verifies that if ChargePoint is offline the connectors on it is considered OUT_OF_ORDER
        Long chargePointId = 1L;
        Long connector1Id = 123L;
        Long connector2Id = 234L;
        Long connectorModel1Id = 5L;
        Long connectorModel2Id = 15L;
        Long chargePointTypeId = 3L;

        Connector con1 = createConnector(chargePointId, connector1Id, connectorModel1Id);
        Connector con2 = createConnector(chargePointId, connector2Id, connectorModel2Id);
        CompleteChargePoint cp = createCompleteChargePoint(chargePointId, chargePointTypeId, Arrays.asList(con1, con2), CompleteChargePoint.OperationalStatusEnum.IN_OPERATION);

        Optional<ConnectorModelAvailabilityRule> rule = Optional.empty();

        Map<Long, ConnectorStatusResponse> conMap = new HashMap<>();
        conMap.put(connector1Id, createConnectorStatus(connector1Id, true, false, false));
        conMap.put(connector1Id, createConnectorStatus(connector2Id, false, false, false));

        Map<Long, ChargePointStatusResponse> cpMap = new HashMap<>();
        cpMap.put(chargePointId, createBasicChargePointStatus(chargePointId, false));

        Map<Long, Long> connectorModelToConnector = new HashMap<>();
        connectorModelToConnector.put(connectorModel1Id, connector1Id);
        connectorModelToConnector.put(connectorModel2Id, connector2Id);

        assertEquals(ConnectorStatus.OUT_OF_ORDER, connectorStatusMapper.getConnectorStatus(con1, cp, rule, conMap, cpMap, connectorModelToConnector));
        assertEquals(ConnectorStatus.OUT_OF_ORDER, connectorStatusMapper.getConnectorStatus(con2, cp, rule, conMap, cpMap, connectorModelToConnector));
    }

    @Test
    public void getConnectorStatusAvailabilityChargePointNotInOperation() throws Exception {
        // Verifies that if ChargePoint is set to be non-operational the connectors on it is considered OUT_OF_ORDER
        Long chargePointId = 1L;
        Long connector1Id = 123L;
        Long connector2Id = 234L;
        Long connectorModel1Id = 5L;
        Long connectorModel2Id = 15L;
        Long chargePointTypeId = 3L;

        Connector con1 = createConnector(chargePointId, connector1Id, connectorModel1Id);
        Connector con2 = createConnector(chargePointId, connector2Id, connectorModel2Id);
        CompleteChargePoint cp = createCompleteChargePoint(chargePointId, chargePointTypeId, Arrays.asList(con1, con2), CompleteChargePoint.OperationalStatusEnum.DISABLED);

        Optional<ConnectorModelAvailabilityRule> rule = Optional.empty();

        Map<Long, ConnectorStatusResponse> conMap = new HashMap<>();
        conMap.put(connector1Id, createConnectorStatus(connector1Id, true, false, false));
        conMap.put(connector1Id, createConnectorStatus(connector2Id, false, false, false));

        Map<Long, ChargePointStatusResponse> cpMap = new HashMap<>();
        cpMap.put(chargePointId, createBasicChargePointStatus(chargePointId, true));

        Map<Long, Long> connectorModelToConnector = new HashMap<>();
        connectorModelToConnector.put(connectorModel1Id, connector1Id);
        connectorModelToConnector.put(connectorModel2Id, connector2Id);

        assertEquals(ConnectorStatus.OUT_OF_ORDER, connectorStatusMapper.getConnectorStatus(con1, cp, rule, conMap, cpMap, connectorModelToConnector));
        assertEquals(ConnectorStatus.OUT_OF_ORDER, connectorStatusMapper.getConnectorStatus(con2, cp, rule, conMap, cpMap, connectorModelToConnector));
    }







}
