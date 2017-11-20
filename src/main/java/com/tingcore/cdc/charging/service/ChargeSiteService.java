package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.mapper.ChargePointSiteMapper;
import com.tingcore.cdc.charging.mapper.ChargeSiteStatuses;
import com.tingcore.cdc.charging.mapper.ConnectorStatusMapper;
import com.tingcore.cdc.charging.mapper.OperationsApiMapper;
import com.tingcore.cdc.charging.model.*;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import com.tingcore.charging.assets.model.ChargePointSiteWithAvailabilityRules;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.charging.operations.api.OperationsApi;
import com.tingcore.charging.operations.model.StatusBatchRequest;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import com.tingcore.commons.rest.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ChargeSiteService {

    private static final Logger LOG = LoggerFactory.getLogger(ChargePointSite.class);

    private final ChargeSitesApi chargeSitesApi;
    private final OperationsApi operationsApi;
    private final OperationsApiMapper operationsApiMapper;
    private final ChargePointSiteMapper chargePointSiteMapper;
    private final ConnectorStatusMapper connectorStatusMapper;


    @Autowired
    public ChargeSiteService(ChargeSitesApi chargeSitesApi, OperationsApi operationsApi, OperationsApiMapper operationsApiMapper, ChargePointSiteMapper chargePointSiteMapper, ConnectorStatusMapper connectorStatusMapper) {
        this.chargeSitesApi = chargeSitesApi;
        this.operationsApi = operationsApi;
        this.operationsApiMapper = operationsApiMapper;
        this.chargePointSiteMapper = chargePointSiteMapper;
        this.connectorStatusMapper = connectorStatusMapper;
    }

    // TODO proper error handling overall
    public PageResponse<BasicChargeSite> getChargeSiteByCoordinate(double lat1, double lng1, double lat2, double lng2) throws ExecutionException, InterruptedException, IOException {
        List<ChargePointSiteWithAvailabilityRules> chargeSiteWithAvailabilityRules = chargeSitesApi.chargeSiteByLocationUsingGET(lat1, lng1, lat2, lng2).get();

        // TODO Remove temporary hack for requests to work when operations service is updated
        chargeSiteWithAvailabilityRules.get(0).getChargePointSite().getChargePoints().remove(2);

        StatusBatchRequest statusBatchRequest = operationsApiMapper.toBatchStatusRequest(
                chargeSiteWithAvailabilityRules.stream().map(ChargePointSiteWithAvailabilityRules::getChargePointSite)
        );

        statusBatchRequest.getStatusRequests().forEach(sr -> {
            LOG.info("CP {}", sr.getChargePointId());
            LOG.info("CONS {}", sr.getConnectorIds());
        });

        Call<StatusBatchResponse> call = operationsApi.getChargePointStatusUsingPOST(
                statusBatchRequest
        );

        LOG.info("URL {}", call.request().url());
        LOG.info("METHOD {}", call.request().method());
        LOG.info("BODY {}", call.request().toString());



        Response<StatusBatchResponse> response = call.execute();

        if(!response.isSuccessful()) {
            LOG.error("ErrorCode: {}", response.code());
            LOG.error(response.message());
        }

        StatusBatchResponse statusBatchResponse = response.body();


        Map<Long, ConnectorStatus> connectorStatusMap = connectorStatusMapper.getStatusMap(chargeSiteWithAvailabilityRules, statusBatchResponse);

        List<BasicChargeSite> previewChargeSites = chargeSiteWithAvailabilityRules.stream()
                .map(cs -> {
                    CompleteChargePointSite ccps = cs.getChargePointSite();

                    ChargeSiteStatuses aggergatedSitesStatues = chargePointSiteMapper.getAggergatedSitesStatues(
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


    public com.tingcore.cdc.charging.model.ChargePointSite getChargeSite(long id) throws ExecutionException, InterruptedException, IOException {
        ChargePointSiteWithAvailabilityRules chargePointSiteWithAvailabilityRules = chargeSitesApi.chargePointSiteWithAvailabilityRulesSiteUsingGET(id).get();
        CompleteChargePointSite ccps = chargePointSiteWithAvailabilityRules.getChargePointSite();
        StatusBatchResponse statusBatchResponse = operationsApi.getChargePointStatusUsingPOST(
                operationsApiMapper.toBatchStatusRequest(ccps)
        ).execute().body();


        Map<Long, ConnectorStatus> connectorStatusMap = connectorStatusMapper.getStatusMap(Collections.singletonList(chargePointSiteWithAvailabilityRules), statusBatchResponse);

        return chargePointSiteMapper.toChargePointSite(
                chargePointSiteWithAvailabilityRules.getChargePointSite(),
                connectorStatusMap);
    }




}
