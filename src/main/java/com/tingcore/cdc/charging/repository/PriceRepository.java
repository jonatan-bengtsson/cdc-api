package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.ConnectorPrice;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.payments.cpo.api.PricesApi;
import com.tingcore.payments.cpo.model.ApiPrice;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class PriceRepository extends AbstractApiRepository {

    private static final Integer DEFAULT_TIME_OUT = 60;

    private final PricesApi pricesApi;

    public PriceRepository(final ObjectMapper objectMapper, final PricesApi pricesApi) {
        super(notNull(objectMapper));
        this.pricesApi = notNull(pricesApi);
    }

    public List<ConnectorPrice> priceForConnectors(final List<ConnectorId> connectorIds) {
        final List<ApiPrice> response = getResponseOrPriceError(
                pricesApi.getPrices(connectorIdsAsLong(connectorIds), Instant.now().toEpochMilli()));
        return response.stream().map(this::apiPriceToModel).collect(toList());
    }

    private List<Long> connectorIdsAsLong(List<ConnectorId> connectorIds) {
        return connectorIds.stream().map(connectorId -> connectorId.value).collect(toList());
    }

    private <T, E extends ExternalApiException> T getResponseOrPriceError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), PriceApiException::new);
    }

    private ConnectorPrice apiPriceToModel(final ApiPrice apiPrice) {
        return new ConnectorPrice(new ConnectorId(apiPrice.getConnectorId()), format("%s\u00A0%s/%s", apiPrice.getPricePerUnit(), apiPrice.getCurrency(), apiPrice.getUnit().getValue()));
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}
