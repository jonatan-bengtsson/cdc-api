package com.tingcore.cdc.charging.mapper;


import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.model.ChargePoint;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.charging.assets.model.*;
import com.tingcore.charging.assets.model.Connector;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static com.tingcore.cdc.charging.mapper.mock.ChargePointFactory.createCompleteChargePoint;
import static com.tingcore.cdc.charging.mapper.mock.ConnectorFactory.createConnector;
import static org.junit.Assert.assertEquals;

@ActiveProfiles(SpringProfilesConstant.UNIT_TEST)
public class ChargePointSiteMapperTest {


    @Test
    public void getAggregatedSitesStatues() throws Exception {

        Long chargePoint1Id = 1L;
        Long connector1Id = 2L;
        Long connector1ModelId = 3L;

        Long chargePoint2Id = 4L;
        Long connector2Id = 5L;
        Long connector2ModelId = 6L;

        Long chargePoint3Id = 5L;
        Long connector3Id = 6L;
        Long connector3ModelId = 7L;

        Long chargePoint4Id = 5L;
        Long connector4Id = 6L;
        Long connector4ModelId = 7L;

        Long chargePoint5Id = 8L;
        Long connector5Id = 9L;
        Long connector5ModelId = 10L;

        Connector con1 = createConnector(chargePoint1Id, connector1Id, connector1ModelId, Connector.ConnectorTypeEnum.TYPE2, 20_000);
        Connector con2 = createConnector(chargePoint2Id, connector2Id, connector2ModelId, Connector.ConnectorTypeEnum.TYPE2, 20_000);
        Connector con3 = createConnector(chargePoint3Id, connector3Id, connector3ModelId, Connector.ConnectorTypeEnum.TYPE2, 30_000);
        Connector con4 = createConnector(chargePoint4Id, connector4Id, connector4ModelId, Connector.ConnectorTypeEnum.TYPE2, 30_000);
        Connector con5 = createConnector(chargePoint5Id, connector5Id, connector5ModelId, Connector.ConnectorTypeEnum.TYPE2, 45_000);


        Map<Long, ConnectorStatus> conMap = new HashMap<>();
        conMap.put(con1.getId(), ConnectorStatus.AVAILABLE);
        conMap.put(con2.getId(), ConnectorStatus.OCCUPIED);
        conMap.put(con3.getId(), ConnectorStatus.OUT_OF_ORDER);
        conMap.put(con4.getId(), ConnectorStatus.RESERVED);
        conMap.put(con5.getId(), ConnectorStatus.AVAILABLE);

        ChargePointSiteStatuses aggergatedSitesStatues = ChargePointSiteMapper.getAggregatedSitesStatues(Arrays.asList(con1, con2, con3, con4, con5), conMap);

        assertEquals(ChargeSiteStatus.AVAILABLE, aggergatedSitesStatues.getStatus());
        assertEquals(ChargeSiteStatus.AVAILABLE, aggergatedSitesStatues.getQuickStatus());


        conMap = new HashMap<>();
        conMap.put(con1.getId(), ConnectorStatus.OCCUPIED);
        conMap.put(con2.getId(), ConnectorStatus.OCCUPIED);
        conMap.put(con3.getId(), ConnectorStatus.OUT_OF_ORDER);
        conMap.put(con4.getId(), ConnectorStatus.RESERVED);
        conMap.put(con5.getId(), ConnectorStatus.AVAILABLE);

        aggergatedSitesStatues = ChargePointSiteMapper.getAggregatedSitesStatues(Arrays.asList(con1, con2, con3, con4, con5), conMap);

        assertEquals(ChargeSiteStatus.OCCUPIED, aggergatedSitesStatues.getStatus());
        assertEquals(ChargeSiteStatus.AVAILABLE, aggergatedSitesStatues.getQuickStatus());


        conMap = new HashMap<>();
        conMap.put(con1.getId(), ConnectorStatus.OUT_OF_ORDER);
        conMap.put(con2.getId(), ConnectorStatus.OUT_OF_ORDER);
        conMap.put(con3.getId(), ConnectorStatus.OUT_OF_ORDER);
        conMap.put(con4.getId(), ConnectorStatus.OUT_OF_ORDER);
        conMap.put(con5.getId(), ConnectorStatus.OCCUPIED);

        aggergatedSitesStatues = ChargePointSiteMapper.getAggregatedSitesStatues(Arrays.asList(con1, con2, con3, con4, con5), conMap);

        assertEquals(ChargeSiteStatus.OUT_OF_ORDER, aggergatedSitesStatues.getStatus());
        assertEquals(ChargeSiteStatus.OCCUPIED, aggergatedSitesStatues.getQuickStatus());

    }

    @Test
    public void toConnector() throws Exception {
        Long chargePointId = 1L;
        Long connectorId = 2L;
        Long connectorModelId = 3L;
        Connector.ConnectorTypeEnum connectorType = Connector.ConnectorTypeEnum.TYPE2;
        double power = 20_000;
        boolean isQuick = false;
        int number = 2;
        String label = "B";
        double current = 120;
        Connector.ModeEnum mode = Connector.ModeEnum.MODE1;
        double voltage = 20;
        Connector.OperationalStatusEnum operationalStatus = Connector.OperationalStatusEnum.OPERATIONAL;
        ConnectorStatus status = ConnectorStatus.AVAILABLE;
        ConnectorPrice price = new ConnectorPrice(new ConnectorId(connectorId), "123.45 SEK/min");

        Connector c = new Connector()
                .id(connectorId)
                .chargePointId(chargePointId)
                .connectorModelId(connectorModelId)
                .connectorType(connectorType)
                .power(power)
                .connectorNumber(number)
                .current(current)
                .mode(mode)
                .operationalStatus(operationalStatus)
                .voltage(voltage);

        com.tingcore.cdc.charging.model.Connector nc = ChargePointSiteMapper.toConnector(c, status, price);

        assertEquals(connectorId, nc.getId());
        assertEquals(number, nc.getNumber());
        assertEquals(label, nc.getLabel());
        assertEquals(status, nc.getStatus());
        assertEquals(connectorType, nc.getType());
        assertEquals(isQuick, nc.isQuick());

    }

    @Test
    public void toChargePoint() throws Exception {
        Long chargePointId = 1L;
        Long chargePointTypeId = 2L;
        Long connectorId = 3L;
        Long connectorModelId = 4L;

        List<Connector> connectors = Arrays.asList(
                createConnector(chargePointId, connectorId, connectorModelId)
        );
        CompleteChargePoint.OperationalStatusEnum status = CompleteChargePoint.OperationalStatusEnum.IN_OPERATION;

        CompleteChargePoint ccp = createCompleteChargePoint(chargePointId, chargePointTypeId, connectors, status);

        Map<Long, ConnectorStatus> conMap = new HashMap<>();
        conMap.put(connectorId, ConnectorStatus.AVAILABLE);

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

        CompleteChargePoint.OperationalStatusEnum chargePointOperationalStatus = CompleteChargePoint.OperationalStatusEnum.IN_OPERATION;
        Address address = new Address()
                .city("City")
                .country("Country")
                .floor("2 vån")
                .number("23")
                .postalCode("123 42")
                .state("Stockholm Län")
                .street("Eventgatan");

        GeoCoordinate geoCoordinate = new GeoCoordinate()
                .latitude(6.44)
                .longitude(5.22);

        Location location = new Location()
                .address(address)
                .geoCoordinate(geoCoordinate)
                .id(locationId);

        List<Connector> connectors = Arrays.asList(
                createConnector(chargePointId, connectorId, connectorModelId, Connector.ConnectorTypeEnum.TYPE2, 20_000)
        );

        CompleteChargePointSite ccps = new CompleteChargePointSite()
                .id(siteId)
                .chargePoints(Collections.singletonList(
                        createCompleteChargePoint(chargePointId, chargePointTypeId, connectors, chargePointOperationalStatus))
                )
                .location(location)
                .name(siteName);

        Map<Long, ConnectorStatus> conStatusMap = new HashMap<>();
        conStatusMap.put(connectorId, ConnectorStatus.AVAILABLE);

        Map<Long, ConnectorPrice> priceInfoMap = new HashMap<>();
        priceInfoMap.put(connectorId, new ConnectorPrice(new ConnectorId(connectorId), "123.45 SEK/min"));

        com.tingcore.cdc.charging.model.ChargePointSite chargePointSite = ChargePointSiteMapper.toChargePointSite(ccps, conStatusMap::get, priceInfoMap::get);

        assertEquals(siteId, chargePointSite.getId());
        assertEquals(chargePointId, chargePointSite.getChargePoints().get(0).getId());
        assertEquals(location, chargePointSite.getLocation());
        assertEquals(siteName, chargePointSite.getName());

        ChargePointTypeStatus chargePointTypeStatus = chargePointSite.getChargePointTypeStatuses().get(0);
        assertEquals(1, chargePointTypeStatus.getAvailable());
        assertEquals(Connector.ConnectorTypeEnum.TYPE2, chargePointTypeStatus.getType());
        assertEquals(AggregatedChargePointTypeStatus.AVAILABLE, chargePointTypeStatus.getAggregatedStatus());

    }
}
