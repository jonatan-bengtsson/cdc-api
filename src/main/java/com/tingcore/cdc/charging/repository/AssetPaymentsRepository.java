package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.exception.NoSessionFoundException;
import com.tingcore.charging.assets.api.ApiForPaymentsApi;
import com.tingcore.charging.assets.model.ChargePointInfo;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.payments.emp.api.ChargesApi;
import com.tingcore.payments.emp.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        return getResponseOrAssetpaymentError(apiForPaymentsApi.findChargePointByConnectorIdUsingGET(id.value));
    }

    private <T, E extends ExternalApiException> T getResponseOrAssetpaymentError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), AssetPaymentsApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}