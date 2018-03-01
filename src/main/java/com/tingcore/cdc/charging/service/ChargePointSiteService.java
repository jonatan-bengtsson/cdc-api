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
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import com.tingcore.charging.assets.model.ChargePointSiteEntity;
import com.tingcore.charging.assets.model.ChargePointSiteSettings;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.charging.operations.api.OperationsApi;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

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
        List<CompleteChargePointSite> allSites = ApiUtils.getResponseOrThrowError(
                assetRepository.execute(chargeSitesApi.findChargePointSitesByLocationUsingGET(lat1, lng1, lat2, lng2)),
                AssetServiceException::new
        );

        List<CompleteChargePointSite> publishedSites = allSites.stream()
                .filter(shouldChargePointSiteBePublished())
                .collect(toList());

        ApiResponse<StatusBatchResponse> response = operationsRepository.execute(
                operationsApi.getChargePointStatusUsingPOST(
                        OperationsApiMapper.toBatchStatusRequest(
                                publishedSites.stream()
                        ))
        );


        if (response.hasError()) {
            // TODO update this according to logging guidelines
            LOG.warn("Error from operations", response.getError());
        }

        return response.getResponseOptional()
                .map(statusBatchResponse -> toChargeSiteWithStatus(publishedSites, statusBatchResponse))
                .orElse(toChargeSiteWithStatusUnknown(publishedSites));
    }


    private PageResponse<BasicChargeSite> toChargeSiteWithStatusUnknown(List<CompleteChargePointSite> completeChargePointSites) {
        List<BasicChargeSite> previewChargeSites = completeChargePointSites.stream()
                .map(ccps -> {
                    ChargePointSiteEntity chargePointSiteEntity = ccps.getChargePointSiteEntity();
                    long chargePointSiteId = chargePointSiteEntity.getMetadata().getId();
                    String chargePointSiteName = chargePointSiteEntity.getData().getName();

                    return new BasicChargeSite(
                            chargePointSiteId,
                            chargePointSiteName,
                            ccps.getLocationEntity().getData().getGeoCoordinate(),
                            ChargeSiteStatus.NO_DATA,
                            ChargeSiteStatus.NO_DATA
                    );
                }).collect(toList());

        return new PageResponse<>(previewChargeSites);
    }

    private PageResponse<BasicChargeSite> toChargeSiteWithStatus(List<CompleteChargePointSite> completeChargePointSites, StatusBatchResponse statusBatchResponse) {
        Map<Long, ConnectorStatusResponse> connectorStatusMap = ConnectorStatusMapper.getStatusMap(statusBatchResponse);

        List<BasicChargeSite> previewChargeSites = completeChargePointSites.stream()
                .map(ccps -> {
                    ChargePointSiteStatuses aggergatedSitesStatues = ChargePointSiteMapper.getAggregatedSitesStatues(
                            ccps.getChargePoints().stream().flatMap(cp -> cp.getConnectorEntities().stream()).collect(toList()),
                            connectorStatusMap
                    );

                    ChargePointSiteEntity chargePointSiteEntity = ccps.getChargePointSiteEntity();
                    long chargePointSiteId = chargePointSiteEntity.getMetadata().getId();
                    String chargePointSiteName = chargePointSiteEntity.getData().getName();
                    return new BasicChargeSite(
                            chargePointSiteId,
                            chargePointSiteName,
                            ccps.getLocationEntity().getData().getGeoCoordinate(),
                            aggergatedSitesStatues.getStatus(),
                            aggergatedSitesStatues.getQuickStatus()
                    );
                }).collect(toList());

        return new PageResponse<>(previewChargeSites);
    }

    /**
     * Fetch a detailed version of a charge point site
     *
     * @param id the id of a charge point site
     * @return A detailed answer of the charge point site including statuses on charge points and connectors
     */
    public com.tingcore.cdc.charging.model.ChargePointSite getChargeSite(long id) {
        CompleteChargePointSite completeChargePointSite = ApiUtils.getResponseOrThrowError(
                assetRepository.execute(chargeSitesApi.findCompleteChargeSiteByIdUsingGET(id)),
                AssetServiceException::new
        );

        if(!shouldChargePointSiteBePublished().test(completeChargePointSite)) {
            throw new EntityNotFoundException("ChargePointSite", Long.toString(id));
        }

        ApiResponse<StatusBatchResponse> statusResponse = operationsRepository.execute(
                operationsApi.getChargePointStatusUsingPOST(
                        OperationsApiMapper.toBatchStatusRequest(completeChargePointSite)
                )
        );

        if (statusResponse.hasError()) {
            // TODO update this according to logging guidelines
            LOG.warn("Error from operations", statusResponse.getError());
        }

        List<ConnectorPrice> connectorPrices = priceRepository.priceForConnectors(completeChargePointSite
                .getChargePoints()
                .stream()
                .flatMap(chargePoint -> chargePoint.getConnectorEntities().stream()).map(connector -> new ConnectorId(connector.getMetadata().getId())).collect(toList()));

        return toChargePointSite(
                completeChargePointSite,
                statusResponse.getResponseOptional().orElse(null),
                connectorPrices
        );
    }

    private ChargePointSite toChargePointSite(CompleteChargePointSite completeChargePointSite,
                                              StatusBatchResponse statusBatchResponse,
                                              List<ConnectorPrice> connectorPrices) {
        final Map<Long, ConnectorStatusResponse> connectorStatusMap = ofNullable(statusBatchResponse).map(ConnectorStatusMapper::getStatusMap).orElse(emptyMap());
        final Map<Long, ConnectorPrice> connectorPriceMap = connectorPrices.stream().collect(toMap(price -> price.connectorId.id, identity()));

        return ChargePointSiteMapper.toChargePointSite(
                completeChargePointSite,
                connectorStatusMap::get,
                connectorPriceMap::get
        );
    }


    private Predicate<CompleteChargePointSite> shouldChargePointSiteBePublished() {
        return s -> s.getChargePointSiteEntity().getData().getSettings().getPublishingChannels().contains(ChargePointSiteSettings.PublishingChannelsEnum.CHARGE_AND_DRIVE_CONNECT_API);
    }

}
