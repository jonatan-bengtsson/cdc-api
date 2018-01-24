package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.exception.NoSessionFoundException;
import com.tingcore.charging.assets.api.ApiForPaymentsApi;
import com.tingcore.charging.assets.model.ChargePointInfo;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.payments.emp.api.ChargesApi;
import com.tingcore.payments.emp.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        try {
            return execute(apiForPaymentsApi.findChargePointByConnectorIdUsingGET(id.value)).getResponse();
        } catch (final RestClientException exception) {
            if (exception instanceof HttpStatusCodeException) {
                final HttpStatusCodeException statusCodeException = HttpStatusCodeException.class.cast(exception);
                if (statusCodeException.getStatusCode().equals(NOT_FOUND)) {
                    throw new NoSessionFoundException("Session not found");
                }
            }
            throw new RuntimeException(exception); // TODO better error handling
        }
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}