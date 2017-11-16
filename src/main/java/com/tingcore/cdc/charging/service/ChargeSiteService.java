package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import com.tingcore.charging.assets.model.ChargePointSiteWithAvailabilityRules;
import com.tingcore.charging.assets.model.CompleteChargePoint;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
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
    public PageResponse<BasicChargeSite> getChargeSiteByCoordinate(double lat1, double lng1, double lat2, double lng2) throws ExecutionException, InterruptedException {


        List<ChargePointSiteWithAvailabilityRules> chargeSiteWithAvailabilityRules = chargeSitesApi.chargeSiteByLocationUsingGET(lat1, lng1, lat2, lng2).get();

        List<BasicChargeSite> previewChargeSites = chargeSiteWithAvailabilityRules.stream()
                .map(cs -> {

                    CompleteChargePointSite ccps = cs.getChargePointSite();
                    return new BasicChargeSite(
                            ccps.getId(),
                            ccps.getName(),
                            ccps.getLocation().getGeoCoordinate(),
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

    public com.tingcore.cdc.charging.model.ChargePointSite getChargeSite(long id) throws ExecutionException, InterruptedException {


        CompleteChargePointSite ccps = chargeSitesApi.completeChargeSiteUsingGET(id).get();

        return new ChargePointSite(
                ccps.getId(),
                ccps.getName(),
                ccps.getLocation(),
                "No description available", // TODO This should be part of CompleteChargeSite
                getStatus(ccps),
                getChargePoints(ccps),
                null
        );
    }

    private List<ChargePoint> getChargePoints(CompleteChargePointSite ccps) {
        return ccps.getChargePoints().stream()
                .map(ccp -> new ChargePoint(
                        ccp.getId(),
                        ccp.getAssetName(),
                        getConnectors(ccp)))
                .collect(Collectors.toList());
    }

    private List<Connector> getConnectors(CompleteChargePoint ccp) {
        return ccp.getConnectors().stream()
                .map(c -> new Connector(
                        c.getId(),
                        getLabel(c.getConnectorNumber()),
                        c.getConnectorNumber(),
                        c.getConnectorType(),
                        isQuickCharge(c),
                        getConnectorStatus(c),
                        "No price available")) // TODO Integrate with payments
                .collect(Collectors.toList());
    }

    private ConnectorStatus getConnectorStatus(com.tingcore.charging.assets.model.Connector c) {
        ConnectorStatus[] statuses = ConnectorStatus.values();
        return statuses[ThreadLocalRandom.current().nextInt(0, statuses.length)];
    }

    private boolean isQuickCharge(com.tingcore.charging.assets.model.Connector c) {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private String getLabel(int connectorNumber) {
        String[] connectorLabels = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T"};

        // ConnectorNumber 0 means all
        int labelIndex = connectorNumber - 1;
        if(labelIndex < 0 || labelIndex > connectorLabels.length) {
            return "NO LABEL PRESENT";
        }

        return connectorLabels[labelIndex];
    }

}
