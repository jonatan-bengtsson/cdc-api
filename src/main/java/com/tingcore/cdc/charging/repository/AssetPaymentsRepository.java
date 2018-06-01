package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.charging.assets.api.ApiForPaymentsApi;
import com.tingcore.charging.assets.model.ChargePointInfo;
import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class AssetPaymentsRepository extends AbstractApiRepository {
    private final ApiForPaymentsApi apiForPaymentsApi;
    private static final Integer DEFAULT_TIME_OUT = 60;

    public AssetPaymentsRepository(final ObjectMapper objectMapper,
                                   final ApiForPaymentsApi apiForPaymentsApi) {
        super(notNull(objectMapper));
        this.apiForPaymentsApi = notNull(apiForPaymentsApi);
    }

    public ChargePointInfo fetchChargePointInfo(final ConnectorId id) {
        return getResponseOrAssetPaymentError(apiForPaymentsApi.findChargePointByConnectorIdUsingGET(id.id));
    }

    private <T, E extends ExternalApiException> T getResponseOrAssetPaymentError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), AssetPaymentsApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}
