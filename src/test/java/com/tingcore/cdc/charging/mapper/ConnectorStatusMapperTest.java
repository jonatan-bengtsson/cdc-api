package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.utils.ConnectorDataUtils;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Map;

import static com.tingcore.cdc.charging.utils.ChargePointDataUtils.createChargePointStatus;
import static org.junit.Assert.assertEquals;

@ActiveProfiles(SpringProfilesConstant.UNIT_TEST)
public class ConnectorStatusMapperTest {

    @Test
    public void getStatusMap() {


        Long chargePointId = 1L;

        Long connector1Id = 20L;

        Long connector2Id = 30L;


        Long connector3Id = 40L;

        Long connector4Id = 50L;


        Long connector5Id = 60L;

        Long connector6Id = 70L;

        Long chargePoint2Id = 2L;
        Long chargePoint3Id = 3L;


        StatusBatchResponse statusBatchResponse = new StatusBatchResponse()
                .statusResponses(Arrays.asList(
                        createChargePointStatus(chargePointId, true,
                                Arrays.asList(
                                        ConnectorDataUtils.createConnectorStatusResponse(connector1Id, ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.IN_USE),
                                        ConnectorDataUtils.createConnectorStatusResponse(connector2Id, ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.IN_USE)
                                )
                        ),
                        createChargePointStatus(chargePoint2Id, true,
                                Arrays.asList(
                                        ConnectorDataUtils.createConnectorStatusResponse(connector3Id, ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.IN_USE),
                                        ConnectorDataUtils.createConnectorStatusResponse(connector4Id, ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.AVAILABLE)
                                )
                        ),
                        createChargePointStatus(chargePoint3Id, false,
                                Arrays.asList(
                                        ConnectorDataUtils.createConnectorStatusResponse(connector5Id, ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, ConnectorStatusResponse.AggregatedConnectorStatusEnum.OUT_OF_ORDER),
                                        ConnectorDataUtils.createConnectorStatusResponse(connector6Id, ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, ConnectorStatusResponse.AggregatedConnectorStatusEnum.OUT_OF_ORDER)
                                )
                        )
                ));


        Map<Long, ConnectorStatusResponse> statusMap = ConnectorStatusMapper.getStatusMap(statusBatchResponse);

        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, statusMap.get(connector1Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, statusMap.get(connector2Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, statusMap.get(connector3Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE, statusMap.get(connector4Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, statusMap.get(connector5Id).getConnectorStatus());
        assertEquals(ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, statusMap.get(connector6Id).getConnectorStatus());

    }

}
