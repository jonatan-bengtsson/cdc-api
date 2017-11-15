package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.model.ChargeSiteStatus;
import com.tingcore.cdc.charging.model.MapPreviewChargeSite;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import com.tingcore.charging.assets.model.*;
import com.tingcore.commons.rest.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ChargeSiteService {

    private final ChargeSitesApi chargeSitesApi;

    @Autowired
    public ChargeSiteService(ChargeSitesApi chargeSitesApi) {
        this.chargeSitesApi = chargeSitesApi;
    }

    // TODO proper error handling overall
    public PageResponse<MapPreviewChargeSite> getChargeSiteByCoordinate(double lat1, double lng1, double lat2, double lng2) throws ExecutionException, InterruptedException {


        List<ChargeSiteWithAvailabilityRules> chargeSiteWithAvailabilityRules = chargeSitesApi.chargeSiteByLocationUsingGET(lat1, lng1, lat2, lng2).get();

        List<MapPreviewChargeSite> previewChargeSites = chargeSiteWithAvailabilityRules.stream()
                .map(cs -> {

                    CompleteChargePointSite ccps = cs.getChargePointSite();
                    return new MapPreviewChargeSite(
                            ccps.getId(),
                            ccps.getName(),
                            ccps.getLocation(),
                            getStatus(ccps)
                    );
                }).collect(Collectors.toList());

        return new PageResponse<>(previewChargeSites);
    }

    private ChargeSiteStatus getStatus(CompleteChargePointSite ccps) {
        // TODO Proper implementation of this logic
        ChargeSiteStatus[] statuses = ChargeSiteStatus.values();
        return statuses[ThreadLocalRandom.current().nextInt(0, statuses.length)];
    }

}
