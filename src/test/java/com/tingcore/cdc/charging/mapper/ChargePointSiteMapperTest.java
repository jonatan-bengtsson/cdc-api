package com.tingcore.cdc.charging.mapper;


import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.model.ChargePoint;
import com.tingcore.cdc.charging.utils.ConnectorDataUtils;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.charging.assets.model.*;
import com.tingcore.charging.assets.model.ChargePointSite;
import com.tingcore.charging.assets.model.Connector;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static com.tingcore.cdc.charging.utils.ChargePointDataUtils.createCompleteChargePoint;
import static com.tingcore.cdc.charging.utils.ConnectorDataUtils.createConnector;
import static org.junit.Assert.assertEquals;

@ActiveProfiles(SpringProfilesConstant.UNIT_TEST)
public class ChargePointSiteMapperTest {


    @Test
    public void getAggregatedSitesStatues() throws Exception {

        Long chargePoint1Id = 1L;
        Long connector1Id = 2L;
        int connector1Number = 1;
        Long connector1ModelId = 3L;

        Long chargePoint2Id = 4L;
        Long connector2Id = 5L;
        int connector2Number = 1;
        Long connector2ModelId = 6L;

        Long chargePoint3Id = 5L;
        Long connector3Id = 6L;
        int connector3Number = 1;
        Long connector3ModelId = 7L;

        Long chargePoint4Id = 5L;
        Long connector4Id = 6L;
        int connector4Number = 1;
        Long connector4ModelId = 7L;

        Long chargePoint5Id = 8L;
        Long connector5Id = 9L;
        int connector5Number = 1;
        Long connector5ModelId = 10L;

        ConnectorEntity con1 = createConnector(chargePoint1Id, connector1Id, connector1ModelId, connector1Number, BasicConnector.ConnectorTypeEnum.TYPE2, 20_000);
        ConnectorEntity con2 = createConnector(chargePoint2Id, connector2Id, connector2ModelId, connector2Number, BasicConnector.ConnectorTypeEnum.TYPE2, 20_000);
        ConnectorEntity con3 = createConnector(chargePoint3Id, connector3Id, connector3ModelId, connector3Number, BasicConnector.ConnectorTypeEnum.TYPE2, 30_000);
        ConnectorEntity con4 = createConnector(chargePoint4Id, connector4Id, connector4ModelId, connector4Number, BasicConnector.ConnectorTypeEnum.TYPE2, 30_000);
        ConnectorEntity con5 = createConnector(chargePoint5Id, connector5Id, connector5ModelId, connector5Number, BasicConnector.ConnectorTypeEnum.TYPE2, 45_000);


        Map<Long, ConnectorStatusResponse> conMap = new HashMap<>();
        conMap.put(con1.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con1.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.AVAILABLE));
        conMap.put(con2.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con2.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.IN_USE));
        conMap.put(con3.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con3.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, ConnectorStatusResponse.AggregatedConnectorStatusEnum.OUT_OF_ORDER));
        conMap.put(con4.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con4.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.RESERVED, ConnectorStatusResponse.AggregatedConnectorStatusEnum.RESERVED));
        conMap.put(con5.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con5.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.AVAILABLE));

        ChargePointSiteStatuses aggergatedSitesStatues = ChargePointSiteMapper.getAggregatedSitesStatues(Arrays.asList(con1, con2, con3, con4, con5), conMap);

        assertEquals(ChargeSiteStatus.AVAILABLE, aggergatedSitesStatues.getStatus());
        assertEquals(ChargeSiteStatus.AVAILABLE, aggergatedSitesStatues.getQuickStatus());


        conMap = new HashMap<>();
        conMap.put(con1.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con1.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.IN_USE));
        conMap.put(con2.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con2.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.IN_USE));
        conMap.put(con3.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con3.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, ConnectorStatusResponse.AggregatedConnectorStatusEnum.OUT_OF_ORDER));
        conMap.put(con4.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con4.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.RESERVED, ConnectorStatusResponse.AggregatedConnectorStatusEnum.RESERVED));
        conMap.put(con5.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con5.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.AVAILABLE));


        aggergatedSitesStatues = ChargePointSiteMapper.getAggregatedSitesStatues(Arrays.asList(con1, con2, con3, con4, con5), conMap);

        assertEquals(ChargeSiteStatus.OCCUPIED, aggergatedSitesStatues.getStatus());
        assertEquals(ChargeSiteStatus.AVAILABLE, aggergatedSitesStatues.getQuickStatus());


        conMap = new HashMap<>();
        conMap.put(con1.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con1.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, ConnectorStatusResponse.AggregatedConnectorStatusEnum.OUT_OF_ORDER));
        conMap.put(con2.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con2.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, ConnectorStatusResponse.AggregatedConnectorStatusEnum.OUT_OF_ORDER));
        conMap.put(con3.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con3.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, ConnectorStatusResponse.AggregatedConnectorStatusEnum.OUT_OF_ORDER));
        conMap.put(con4.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con4.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.OUT_OF_ORDER, ConnectorStatusResponse.AggregatedConnectorStatusEnum.OUT_OF_ORDER));
        conMap.put(con5.getMetadata().getId(), ConnectorDataUtils.createConnectorStatusResponse(con5.getMetadata().getId(),
                ConnectorStatusResponse.ConnectorStatusEnum.IN_USE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.IN_USE));

        aggergatedSitesStatues = ChargePointSiteMapper.getAggregatedSitesStatues(Arrays.asList(con1, con2, con3, con4, con5), conMap);

        assertEquals(ChargeSiteStatus.OUT_OF_ORDER, aggergatedSitesStatues.getStatus());
        assertEquals(ChargeSiteStatus.OCCUPIED, aggergatedSitesStatues.getQuickStatus());

    }

    @Test
    public void toConnector() throws Exception {
        Long chargePointId = 1L;
        Long connectorId = 2L;
        Long connectorModelId = 3L;
        BasicConnector.ConnectorTypeEnum connectorType = BasicConnector.ConnectorTypeEnum.TYPE2;
        double power = 20_000;
        boolean isQuick = false;
        int number = 2;
        String label = "B";
        double current = 120;
        ConnectorCapability.ModeEnum mode = ConnectorCapability.ModeEnum.MODE1;
        double voltage = 20;
        BasicConnector.AdminStatusEnum adminStatus = BasicConnector.AdminStatusEnum.OPERATIONAL;
        ConnectorStatusResponse status = ConnectorDataUtils.createConnectorStatusResponse(connectorId,
                ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.AVAILABLE);
        ConnectorPrice price = new ConnectorPrice(new ConnectorId(connectorId), "123.45 SEK/min");


        ConnectorEntity ce = new ConnectorEntity()
                .metadata(new EntityMetadata()
                        .id(connectorId))
                .data(new Connector()
                        .basicConnector(new BasicConnector()
                                .chargePointId(chargePointId)
                                .connectorNumber(number)
                                .connectorType(connectorType)
                                .adminStatus(adminStatus)
                        )
                        .connectorCapability(new ConnectorCapability()
                                .current(current)
                                .power(power)
                                .mode(mode)
                                .voltage(voltage)));

        com.tingcore.cdc.charging.model.Connector nc = ChargePointSiteMapper.toConnector(ce, status, price);

        assertEquals(connectorId, nc.getId());
        assertEquals(number, nc.getNumber());
        assertEquals(label, nc.getLabel());
        assertEquals(ConnectorStatus.AVAILABLE, nc.getStatus());
        assertEquals(connectorType, nc.getType());
        assertEquals(isQuick, nc.isQuick());

    }

    @Test
    public void toChargePoint() throws Exception {
        Long chargePointId = 1L;
        Long chargePointTypeId = 2L;
        Long connectorId = 3L;
        int connectorNumber = 1;
        Long connectorModelId = 4L;

        List<ConnectorEntity> connectors = Arrays.asList(
                createConnector(chargePointId, connectorId, connectorModelId, connectorNumber)
        );
        BasicChargePoint.AdminStatusEnum status = BasicChargePoint.AdminStatusEnum.IN_OPERATION;

        CompleteChargePoint ccp = createCompleteChargePoint(chargePointId, chargePointTypeId, connectors, status);

        Map<Long, ConnectorStatusResponse> conMap = new HashMap<>();
        conMap.put(connectorId, ConnectorDataUtils.createConnectorStatusResponse(connectorId, ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.AVAILABLE));

        Map<Long, ConnectorPrice> priMap = new HashMap<>();
        priMap.put(connectorId, new ConnectorPrice(new ConnectorId(connectorId), "123.45 SEK/min"));

        ChargePoint chargePoint = ChargePointSiteMapper.toChargePoint(ccp, conMap::get, priMap::get);

        assertEquals(chargePointId, chargePoint.getId());
        assertEquals(String.format("ASSET%d", chargePointId), chargePoint.getAssetName());
        assertEquals(connectorId, chargePoint.getConnectors().get(0).getId());
    }

    @Test
    public void toChargePointSite() throws Exception {
        Long siteId = 1L;
        Long chargePointId = 2L;
        Long chargePointTypeId = 3L;
        Long connectorId = 4L;
        Long connectorModelId = 5L;
        Long locationId = 6L;
        String siteName = "Tingcore HQ";

        BasicChargePoint.AdminStatusEnum chargePointAdminStatus = BasicChargePoint.AdminStatusEnum.IN_OPERATION;
        Address address = new Address()
                .city("City")
                .countryIsoCode("SE")
                .floor("2 vån")
                .number("23")
                .postalCode("123 42")
                .region("Stockholm Län")
                .street("Eventgatan");

        GeoCoordinate geoCoordinate = new GeoCoordinate()
                .latitude(6.44)
                .longitude(5.22);

        LocationEntity location = new LocationEntity()
                .metadata(new EntityMetadata().id(locationId))
                .data(new Location()
                        .address(address)
                        .geoCoordinate(geoCoordinate));

        List<ConnectorEntity> connectors = Arrays.asList(
                createConnector(chargePointId, connectorId, connectorModelId, 1, BasicConnector.ConnectorTypeEnum.TYPE2, 20_000)
        );

        CompleteChargePointSite ccps = new CompleteChargePointSite()
                .chargePointSiteEntity(new ChargePointSiteEntity()
                        .data(new ChargePointSite()
                                .name(siteName)
                                .locationId(locationId))
                        .metadata(new EntityMetadata().id(siteId)))
                .chargePoints(Collections.singletonList(
                        createCompleteChargePoint(chargePointId, chargePointTypeId, connectors, chargePointAdminStatus))
                )
                .locationEntity(location);


        Map<Long, ConnectorStatusResponse> conStatusMap = new HashMap<>();
        conStatusMap.put(connectorId, ConnectorDataUtils.createConnectorStatusResponse(connectorId, ConnectorStatusResponse.ConnectorStatusEnum.AVAILABLE, ConnectorStatusResponse.AggregatedConnectorStatusEnum.AVAILABLE));

        Map<Long, ConnectorPrice> priceInfoMap = new HashMap<>();
        priceInfoMap.put(connectorId, new ConnectorPrice(new ConnectorId(connectorId), "123.45 SEK/min"));

        com.tingcore.cdc.charging.model.ChargePointSite chargePointSite = ChargePointSiteMapper.toChargePointSite(ccps, conStatusMap::get, priceInfoMap::get);

        assertEquals(siteId, chargePointSite.getId());
        assertEquals(chargePointId, chargePointSite.getChargePoints().get(0).getId());
        assertEquals(location.getData(), chargePointSite.getLocation());
        assertEquals(siteName, chargePointSite.getName());

        ChargePointTypeStatus chargePointTypeStatus = chargePointSite.getChargePointTypeStatuses().get(0);
        assertEquals(1, chargePointTypeStatus.getAvailable());
        assertEquals(BasicConnector.ConnectorTypeEnum.TYPE2, chargePointTypeStatus.getType());
        assertEquals(AggregatedChargePointTypeStatus.AVAILABLE, chargePointTypeStatus.getAggregatedStatus());

    }
}
