package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.mapper.ChargePointSiteMapper;
import com.tingcore.cdc.charging.mapper.ChargePointSiteStatuses;
import com.tingcore.cdc.charging.mapper.ConnectorStatusMapper;
import com.tingcore.cdc.charging.mapper.OperationsApiMapper;
import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.repository.AssetRepository;
import com.tingcore.cdc.charging.repository.OperationsRepository;
import com.tingcore.cdc.charging.repository.PriceRepository;
import com.tingcore.cdc.controller.ApiUtils;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import com.tingcore.charging.assets.model.ChargePointSiteWithAvailabilityRules;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.charging.operations.api.OperationsApi;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChargePointSiteService {

    private static final Logger LOG = LoggerFactory.getLogger(ChargePointSiteService.class);

    private final ChargeSitesApi chargeSitesApi;
    private final OperationsApi operationsApi;

    private final AssetRepository assetRepository;
    private final OperationsRepository operationsRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public ChargePointSiteService(AssetRepository assetRepository, OperationsRepository operationsRepository, PriceRepository priceRepository) {
        this.chargeSitesApi = assetRepository.getChargeSitesApi();
        this.operationsApi = operationsRepository.getOperationsApi();
        this.assetRepository = assetRepository;
        this.operationsRepository = operationsRepository;
        this.priceRepository = priceRepository;
    }

    /**
     * Given two coordinates representing a bounding box
     * returns basic versions of all charge point sites within the bounding box with some aggregated status
     *
     * @param lat1 latitude component of first coordinate
     * @param lng1 longitude component of first coordinate
     * @param lat2 latitude component of second coordinate
     * @param lng2 longitude component of second coordinate
     * @return response with basic versions of sites within the bounding box represented by the first and second coordinates.
     */
    public PageResponse<BasicChargeSite> getChargeSiteByCoordinate(double lat1, double lng1, double lat2, double lng2) {
        List<ChargePointSiteWithAvailabilityRules> chargeSiteWithAvailabilityRules = ApiUtils.getResponseOrThrowError(
                assetRepository.execute(chargeSitesApi.chargeSiteByLocationUsingGET(lat1, lng1, lat2, lng2)),
                AssetServiceException::new
        );

        ApiResponse<StatusBatchResponse> response = operationsRepository.execute(
                operationsApi.getChargePointStatusUsingPOST(
                        OperationsApiMapper.toBatchStatusRequest(
                                chargeSiteWithAvailabilityRules.stream().map(ChargePointSiteWithAvailabilityRules::getChargePointSite)
                        ))
        );


        if (response.hasError()) {
            // TODO update this according to logging guidelines
            LOG.warn("Error from operations", response.getError());
        }

        return response.getResponseOptional()
                .map(statusBatchResponse -> toChargeSiteWithStatus(chargeSiteWithAvailabilityRules, statusBatchResponse))
                .orElse(toChargeSiteWithStatusUnknown(chargeSiteWithAvailabilityRules));
    }

    private PageResponse<BasicChargeSite> toChargeSiteWithStatusUnknown(List<ChargePointSiteWithAvailabilityRules> chargeSiteWithAvailabilityRules) {
        List<BasicChargeSite> previewChargeSites = chargeSiteWithAvailabilityRules.stream()
                .map(cs -> {
                    CompleteChargePointSite ccps = cs.getChargePointSite();

                    return new BasicChargeSite(
                            ccps.getId(),
                            ccps.getName(),
                            ccps.getLocation().getGeoCoordinate(),
                            ChargeSiteStatus.NO_DATA,
                            ChargeSiteStatus.NO_DATA
                    );
                }).collect(Collectors.toList());

        return new PageResponse<>(previewChargeSites);
    }

    private PageResponse<BasicChargeSite> toChargeSiteWithStatus(List<ChargePointSiteWithAvailabilityRules> chargeSiteWithAvailabilityRules, StatusBatchResponse statusBatchResponse) {
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
     *
     * @param id the id of a charge point site
     * @return A detailed answer of the charge point site including statuses on charge points and connectors
     */
    public com.tingcore.cdc.charging.model.ChargePointSite getChargeSite(long id) {
        ChargePointSiteWithAvailabilityRules chargePointSiteWithAvailabilityRules = ApiUtils.getResponseOrThrowError(
                assetRepository.execute(chargeSitesApi.chargePointSiteWithAvailabilityRulesSiteUsingGET(id)),
                AssetServiceException::new
        );

        CompleteChargePointSite ccps = chargePointSiteWithAvailabilityRules.getChargePointSite();

        ApiResponse<StatusBatchResponse> response = operationsRepository.execute(
                operationsApi.getChargePointStatusUsingPOST(
                        OperationsApiMapper.toBatchStatusRequest(ccps)
                )
        );

        if (response.hasError()) {
            // TODO update this according to logging guidelines
            LOG.warn("Error from operations", response.getError());
        }

        return response.getResponseOptional()
                .map(statusBatchResponse -> toChargePointSiteWithStatus(chargePointSiteWithAvailabilityRules, statusBatchResponse))
                .orElse(toChargePointSiteWithUnknownStatus(chargePointSiteWithAvailabilityRules));

    }

    private ChargePointSite toChargePointSiteWithUnknownStatus(ChargePointSiteWithAvailabilityRules chargePointSiteWithAvailabilityRules) {
        return ChargePointSiteMapper.toChargePointSite(chargePointSiteWithAvailabilityRules.getChargePointSite());
    }

    private ChargePointSite toChargePointSiteWithStatus(ChargePointSiteWithAvailabilityRules chargePointSiteWithAvailabilityRules, StatusBatchResponse statusBatchResponse) {
        final Map<Long, ConnectorStatus> connectorStatusMap = ConnectorStatusMapper.getStatusMap(Collections.singletonList(chargePointSiteWithAvailabilityRules), statusBatchResponse);

        return ChargePointSiteMapper.toChargePointSite(
                chargePointSiteWithAvailabilityRules.getChargePointSite(),
                connectorStatusMap::get,
                this::priceForConnector
        );
    }

    private PriceInformation priceForConnector(final Long connectorId) {
        try {
            return priceRepository.priceForConnector(new ConnectorId(connectorId));
        } catch (final RuntimeException exception) {
            return null;
        }
    }


}
