package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.model.ChargeSiteStatus;
import com.tingcore.cdc.charging.model.MapPreviewChargeSite;
import com.tingcore.charging.assets.model.Address;
import com.tingcore.charging.assets.model.GeoCoordinate;
import com.tingcore.charging.assets.model.Location;
import com.tingcore.commons.rest.PageResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ChargeSiteService {

    // TODO Remove temporary mock
    private static Location create() {
        GeoCoordinate geoCoordinate = new GeoCoordinate();
        geoCoordinate.setLatitude(59.369956289349005);
        geoCoordinate.setLongitude(18.001026213169098);

        return create(geoCoordinate);
    }

    // TODO Remove temporary mock
    private static Location create(GeoCoordinate coordinate) {
        Address address = new Address();

        address.setCity("Stockholm");
        address.setCity("Sweden");
        address.setPostalCode("SE-18754");
        address.setState("Stockholms län");
        address.setStreet("Solnastigen");
        address.setNumber("17C");
        address.setFloor("våning 2");

        Location l = new Location();
        l.setAddress(address);
        l.setGeoCoordinate(coordinate);

        return l;

    }

    public PageResponse<MapPreviewChargeSite> getChargeSiteByCoordinate(double lat1, double lng1, double lat2, double lng2) {
        ChargeSiteStatus[] statuses = ChargeSiteStatus.values();

        GeoCoordinate geoCoordinate = new GeoCoordinate();
        geoCoordinate.setLatitude(59.369956289349005);
        geoCoordinate.setLongitude(18.001026213169098);

        GeoCoordinate geoCoordinate1 = new GeoCoordinate();
        geoCoordinate.setLatitude(60.369956289349005);
        geoCoordinate.setLongitude(18.001026213169098);

        GeoCoordinate geoCoordinate2 = new GeoCoordinate();
        geoCoordinate.setLatitude(59.369956289349005);
        geoCoordinate.setLongitude(19.001026213169098);


        // TODO Remove temporary mock
        return new PageResponse<>(Arrays.asList(
                new MapPreviewChargeSite(1, "Site 1", create(geoCoordinate), statuses[ThreadLocalRandom.current().nextInt(0, statuses.length)]),
                new MapPreviewChargeSite(2, "Site 2", create(geoCoordinate1), statuses[ThreadLocalRandom.current().nextInt(0, statuses.length)]),
                new MapPreviewChargeSite(3, "Site 3", create(geoCoordinate2), statuses[ThreadLocalRandom.current().nextInt(0, statuses.length)])
        ));
    }

}
