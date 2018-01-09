package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.mapper.mock.ConnectorStatusResponseFactory;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.charging.assets.model.AvailabilityRulesWithChargePointId;
import com.tingcore.charging.assets.model.BasicChargePoint;
import com.tingcore.charging.assets.model.ChargePoint;
import com.tingcore.charging.assets.model.ChargePointConfiguration;
import com.tingcore.charging.assets.model.ChargePointEntity;
import com.tingcore.charging.assets.model.ChargePointSiteEntity;
import com.tingcore.charging.assets.model.ChargePointSiteWithAvailabilityRules;
import com.tingcore.charging.assets.model.CompleteChargePoint;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.charging.assets.model.ConnectorEntity;
import com.tingcore.charging.assets.model.ConnectorModelAvailabilityRule;
import com.tingcore.charging.assets.model.ConnectorModelAvailabilityRuleEntity;
import com.tingcore.charging.assets.model.EntityMetadata;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.tingcore.cdc.charging.mapper.mock.ChargePointFactory.createChargePointStatus;
import static com.tingcore.cdc.charging.mapper.mock.ConnectorFactory.createConnector;
import static org.junit.Assert.assertEquals;

@ActiveProfiles(SpringProfilesConstant.UNIT_TEST)
public class ConnectorStatusMapperTest {

    @Test
    public void getStatusMap() throws Exception {

        Long chargePointModelId = 5L;
        Long chargePointModel2Id = 10L;
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
                                                        chargePointModel2Id,
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
                                        ConnectorStatusResponseFactory.create(connector1Id, ConnectorStatusResponse.ConnectorStatusEnum.IN_USE),
                                        ConnectorStatusResponseFactory.create(connector2Id, ConnectorStatusResponse.ConnectorStatusEnum.IN_USE)
                                )
                        ),
                        createChargePointStatus(chargePoint2Id, true,
                                Arrays.asList(
                                        ConnectorStatusResponseFactory.create(connector3Id, ConnectorStatusResponse.ConnectorStatusEnum.IN_USE),
                                        ConnectorStatusResponseFactory.create(connector4Id, ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE)
                                )
                        ),
                        createChargePointStatus(chargePoint3Id, false,
                                Arrays.asList(
                                        ConnectorStatusResponseFactory.create(connector5Id, ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER),
                                        ConnectorStatusResponseFactory.create(connector6Id, ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER)
                                )
                        )
                ));


        Map<Long, ConnectorStatusResponse> statusMap = ConnectorStatusMapper.getStatusMap(sites, statusBatchResponse);

        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, statusMap.get(connector1Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, statusMap.get(connector2Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, statusMap.get(connector3Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE, statusMap.get(connector4Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, statusMap.get(connector5Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, statusMap.get(connector6Id).getConnectorStatus());

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
}
