package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.mapper.ChargePointSiteMapper;
import com.tingcore.cdc.charging.mapper.ChargePointSiteStatuses;
import com.tingcore.cdc.charging.mapper.ConnectorStatusMapper;
import com.tingcore.cdc.charging.mapper.OperationsApiMapper;
import com.tingcore.cdc.charging.model.*;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import com.tingcore.charging.assets.model.ChargePointSiteWithAvailabilityRules;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.charging.operations.api.OperationsApi;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import com.tingcore.commons.rest.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ChargeSiteService {

    private static final Logger LOG = LoggerFactory.getLogger(ChargePointSite.class);

    private final ChargeSitesApi chargeSitesApi;
    private final OperationsApi operationsApi;

    @Autowired
    public ChargeSiteService(ChargeSitesApi chargeSitesApi, OperationsApi operationsApi) {
        this.chargeSitesApi = chargeSitesApi;
        this.operationsApi = operationsApi;
    }

    // TODO proper error handling overall

    /**
     * Given two coordinates representing a bounding box
     * returns basic versions of all charge point sites within the bounding box
     * @param lat1 latitude component of first coordinate
     * @param lng1 longitude component of first coordinate
     * @param lat2 latitude component of second coordinate
     * @param lng2 longitude component of second coordinate
     * @return response with basic versions of sites within the bounding box represented by the first and second coordinates.
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public PageResponse<BasicChargeSite> getChargeSiteByCoordinate(double lat1, double lng1, double lat2, double lng2) throws ExecutionException, InterruptedException, IOException {
        List<ChargePointSiteWithAvailabilityRules> chargeSiteWithAvailabilityRules = chargeSitesApi.chargeSiteByLocationUsingGET(lat1, lng1, lat2, lng2).get();

        StatusBatchResponse statusBatchResponse = operationsApi.getChargePointStatusUsingPOST(
                OperationsApiMapper.toBatchStatusRequest(
                        chargeSiteWithAvailabilityRules.stream().map(ChargePointSiteWithAvailabilityRules::getChargePointSite)
        )).execute().body();


        Map<Long, ConnectorStatus> connectorStatusMap = ConnectorStatusMapper.getStatusMap(chargeSiteWithAvailabilityRules, statusBatchResponse);

        List<BasicChargeSite> previewChargeSites = chargeSiteWithAvailabilityRules.stream()
                .map(cs -> {
                    CompleteChargePointSite ccps = cs.getChargePointSite();
                    ChargePointSiteStatuses aggergatedSitesStatues = ChargePointSiteMapper.getAggregatedSitesStatues(
                            ccps.getChargePoints().stream().flatMap(cp -> cp.getConnectors().stream()).collect(Collectors.toList()),
                            connectorStatusMap
                    );

                    return new BasicChargeSite(
                            ccps.getId(),
                            ccps.getName(),
                            ccps.getLocation().getGeoCoordinate(),
                            aggergatedSitesStatues.getStatus(),
                            aggergatedSitesStatues.getQuickStatus()
                    );
                }).collect(Collectors.toList());

        return new PageResponse<>(previewChargeSites);
    }

    /**
     * Fetch a detailed version of a charge point site
     * @param id the id of a charge point site
     * @return A detailed answer of the charge point site including statuses on charge points and connectors
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public com.tingcore.cdc.charging.model.ChargePointSite getChargeSite(long id) throws ExecutionException, InterruptedException, IOException {
        ChargePointSiteWithAvailabilityRules chargePointSiteWithAvailabilityRules = chargeSitesApi.chargePointSiteWithAvailabilityRulesSiteUsingGET(id).get();
        CompleteChargePointSite ccps = chargePointSiteWithAvailabilityRules.getChargePointSite();

        StatusBatchResponse statusBatchResponse = operationsApi.getChargePointStatusUsingPOST(
                OperationsApiMapper.toBatchStatusRequest(ccps)
        ).execute().body();

        Map<Long, ConnectorStatus> connectorStatusMap = ConnectorStatusMapper.getStatusMap(Collections.singletonList(chargePointSiteWithAvailabilityRules), statusBatchResponse);

        return ChargePointSiteMapper.toChargePointSite(
                chargePointSiteWithAvailabilityRules.getChargePointSite(),
                connectorStatusMap);
    }




}
