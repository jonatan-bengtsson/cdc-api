package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.model.ConnectorStatus;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.charging.assets.model.*;
import com.tingcore.charging.operations.model.ChargePointStatusResponse;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static com.tingcore.cdc.charging.mapper.mock.ChargePointFactory.createBasicChargePointStatus;
import static com.tingcore.cdc.charging.mapper.mock.ChargePointFactory.createChargePointStatus;
import static com.tingcore.cdc.charging.mapper.mock.ChargePointFactory.createCompleteChargePoint;
import static com.tingcore.cdc.charging.mapper.mock.ConnectorFactory.createConnector;
import static com.tingcore.cdc.charging.mapper.mock.ConnectorFactory.createConnectorStatus;
import static org.junit.Assert.assertEquals;

@ActiveProfiles(SpringProfilesConstant.UNIT_TEST)
public class ConnectorStatusMapperTest {

    @Test
    public void getStatusMap() throws Exception {

        Long chargePointModelId = 5L;
        Long chargePointId = 1L;

        Long connector1Id = 20L;
        Integer connector1Number = 1;
        Long connector2Id = 30L;
        Integer connector2Number = 2;

        Long connector3Id = 40L;
        Integer connector3Number = 1;
        Long connector4Id = 50L;
        Integer connector4Number = 2;

        Long connector5Id = 60L;
        Integer connector5Number = 1;
        Long connector6Id = 70L;
        Integer connector6Number = 2;

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
                                createAvailabilityRule(chargePointModelId, Arrays.asList(connector1Number, connector2Number))
                        )
                        .chargePointSite(
                                new CompleteChargePointSite()
                                        .chargePointSiteEntity(new ChargePointSiteEntity()
                                                .metadata(new EntityMetadata()
                                                        .id(10L)))
                                        .chargePoints(Arrays.asList(
                                                createCompleteChargePointWithTwoConnectors(
                                                        chargePointId,
                                                        BasicChargePoint.OperationalStatusEnum.IN_OPERATION,
                                                        chargePointModelId,
                                                        createConnector(chargePointId, connector1Id, connectorModel1Id, connector1Number),
                                                        createConnector(chargePointId, connector2Id, connectorModel2Id, connector2Number)
                                                ),
                                                createCompleteChargePointWithTwoConnectors(
                                                        chargePoint2Id,
                                                        BasicChargePoint.OperationalStatusEnum.IN_OPERATION,
                                                        chargePointModelId,
                                                        createConnector(chargePoint2Id, connector3Id, connectorModel3Id, connector1Number),
                                                        createConnector(chargePoint2Id, connector4Id, connectorModel4Id, connector2Number)
                                                ),
                                                createCompleteChargePointWithTwoConnectors(
                                                        chargePoint3Id,
                                                        BasicChargePoint.OperationalStatusEnum.IN_OPERATION,
                                                        chargePointModelId,
                                                        createConnector(chargePoint3Id, connector5Id, connectorModel5Id, connector1Number),
                                                        createConnector(chargePoint3Id, connector6Id, connectorModel6Id, connector2Number)
                                                )
                                        ))
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
                                        createConnectorStatus(connector3Id, false, false, false),
                                        createConnectorStatus(connector4Id, false, false, false)
                                )
                        ),
                        createChargePointStatus(chargePoint3Id, false,
                                Arrays.asList(
                                        createConnectorStatus(connector5Id, false, false, false),
                                        createConnectorStatus(connector6Id, false, false, false)
                                )
                        )
                ));


        Map<Long, ConnectorStatus> statusMap = ConnectorStatusMapper.getStatusMap(sites, statusBatchResponse);

        assertEquals(ConnectorStatus.OCCUPIED, statusMap.get(connector1Id));
        assertEquals(ConnectorStatus.OCCUPIED, statusMap.get(connector2Id));
        assertEquals(ConnectorStatus.AVAILABLE, statusMap.get(connector3Id));
        assertEquals(ConnectorStatus.AVAILABLE, statusMap.get(connector4Id));
        assertEquals(ConnectorStatus.OUT_OF_ORDER, statusMap.get(connector5Id));
        assertEquals(ConnectorStatus.OUT_OF_ORDER, statusMap.get(connector6Id));

    }

    private AvailabilityRulesWithChargePointId createAvailabilityRule(Long chargePointModelId, List<Integer> connectorNumbers) {


        ConnectorModelAvailabilityRule r = new ConnectorModelAvailabilityRule()
                .chargePointModelId(chargePointModelId)
                .connectorNumbers(connectorNumbers);

        ConnectorModelAvailabilityRuleEntity connectorModelAvailabilityRuleEntity = new ConnectorModelAvailabilityRuleEntity()
                .data(r)
                .metadata(new EntityMetadata());

        return new AvailabilityRulesWithChargePointId()
                .chargePointId(chargePointModelId)
                .rules(
                        Collections.singletonList(
                            connectorModelAvailabilityRuleEntity
                        )
                );
    }

    private CompleteChargePoint createCompleteChargePointWithTwoConnectors(Long chargePointId, BasicChargePoint.OperationalStatusEnum chargePointStatus, Long chargePointModelId, ConnectorEntity c1, ConnectorEntity c2){
        BasicChargePoint basicChargePoint = new BasicChargePoint()
                .operationalStatus(chargePointStatus)
                .chargePointModelId(chargePointModelId);

        ChargePoint chargePoint = new ChargePoint()
                .basicChargePoint(basicChargePoint)
                .chargePointConfiguration(new ChargePointConfiguration());

        ChargePointEntity chargePointEntity = new ChargePointEntity()
                .data(chargePoint)
                .metadata(new EntityMetadata().id(chargePointId));

        return new CompleteChargePoint()
                .chargePointEntity(chargePointEntity)
                .connectorEntities(Arrays.asList(c1,c2));

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
        int connectorNumber1 = 1;
        int connectorNumber2 = 2;

        ConnectorEntity con1 = createConnector(chargePointId, connector1Id, connectorModel1Id, connectorNumber1);
        ConnectorEntity con2 = createConnector(chargePointId, connector2Id, connectorModel2Id, connectorNumber2);
        CompleteChargePoint cp = createCompleteChargePoint(chargePointId, chargePointTypeId, Arrays.asList(con1, con2), BasicChargePoint.OperationalStatusEnum.IN_OPERATION);

        Optional<ConnectorModelAvailabilityRule> rule = Optional.of(
                new ConnectorModelAvailabilityRule()
                        .chargePointModelId(chargePointTypeId)
                        .connectorNumbers(Arrays.asList(connectorNumber1, connectorNumber2))
        );


        Map<Long, ConnectorStatusResponse> conMap = new HashMap<>();
        conMap.put(connector1Id, createConnectorStatus(connector1Id, true, false, false));
        conMap.put(connector2Id, createConnectorStatus(connector2Id, false, false, false));


        Map<Long, ChargePointStatusResponse> cpMap = new HashMap<>();
        cpMap.put(chargePointId, createBasicChargePointStatus(chargePointId, true));

        Map<Integer, Long> connectorNumberToConnector = new HashMap<>();
        connectorNumberToConnector.put(connectorNumber1, connector1Id);
        connectorNumberToConnector.put(connectorNumber2, connector2Id);

        assertEquals(ConnectorStatus.OCCUPIED, ConnectorStatusMapper.calculateConnectorStatus(con1, cp, rule, conMap, cpMap, connectorNumberToConnector));
        assertEquals(ConnectorStatus.OCCUPIED, ConnectorStatusMapper.calculateConnectorStatus(con2, cp, rule, conMap, cpMap, connectorNumberToConnector));
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
        int connectorNumber1 = 1;
        int connectorNumber2 = 2;

        ConnectorEntity con1 = createConnector(chargePointId, connector1Id, connectorModel1Id, connectorNumber1);
        ConnectorEntity con2 = createConnector(chargePointId, connector2Id, connectorModel2Id, connectorNumber2);
        CompleteChargePoint cp = createCompleteChargePoint(chargePointId, chargePointTypeId, Arrays.asList(con1, con2), BasicChargePoint.OperationalStatusEnum.IN_OPERATION);

        Optional<ConnectorModelAvailabilityRule> rule = Optional.empty();

        Map<Long, ConnectorStatusResponse> conMap = new HashMap<>();
        conMap.put(connector1Id, createConnectorStatus(connector1Id, true, false, false));
        conMap.put(connector1Id, createConnectorStatus(connector2Id, false, false, false));

        Map<Long, ChargePointStatusResponse> cpMap = new HashMap<>();
        cpMap.put(chargePointId, createBasicChargePointStatus(chargePointId, false));

        Map<Integer, Long> connectorNumberToConnector = new HashMap<>();
        connectorNumberToConnector.put(connectorNumber1, connector1Id);
        connectorNumberToConnector.put(connectorNumber2, connector2Id);

        assertEquals(ConnectorStatus.OUT_OF_ORDER, ConnectorStatusMapper.calculateConnectorStatus(con1, cp, rule, conMap, cpMap, connectorNumberToConnector));
        assertEquals(ConnectorStatus.OUT_OF_ORDER, ConnectorStatusMapper.calculateConnectorStatus(con2, cp, rule, conMap, cpMap, connectorNumberToConnector));
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
        int connectorNumber1 = 1;
        int connectorNumber2 = 2;


        ConnectorEntity con1 = createConnector(chargePointId, connector1Id, connectorModel1Id, connectorNumber1);
        ConnectorEntity con2 = createConnector(chargePointId, connector2Id, connectorModel2Id, connectorNumber2);
        CompleteChargePoint cp = createCompleteChargePoint(chargePointId, chargePointTypeId, Arrays.asList(con1, con2), BasicChargePoint.OperationalStatusEnum.DISABLED);

        Optional<ConnectorModelAvailabilityRule> rule = Optional.empty();

        Map<Long, ConnectorStatusResponse> conMap = new HashMap<>();
        conMap.put(connector1Id, createConnectorStatus(connector1Id, true, false, false));
        conMap.put(connector1Id, createConnectorStatus(connector2Id, false, false, false));

        Map<Long, ChargePointStatusResponse> cpMap = new HashMap<>();
        cpMap.put(chargePointId, createBasicChargePointStatus(chargePointId, true));

        Map<Integer, Long> connectorNumberToConnector = new HashMap<>();
        connectorNumberToConnector.put(connectorNumber1, connector1Id);
        connectorNumberToConnector.put(connectorNumber2, connector2Id);

        assertEquals(ConnectorStatus.OUT_OF_ORDER, ConnectorStatusMapper.calculateConnectorStatus(con1, cp, rule, conMap, cpMap, connectorNumberToConnector));
        assertEquals(ConnectorStatus.OUT_OF_ORDER, ConnectorStatusMapper.calculateConnectorStatus(con2, cp, rule, conMap, cpMap, connectorNumberToConnector));
    }







}
