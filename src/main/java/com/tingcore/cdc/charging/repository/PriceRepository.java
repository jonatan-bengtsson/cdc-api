package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.ConnectorPrice;
import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.payments.cpo.api.PricesApi;
import com.tingcore.payments.cpo.model.ApiPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.tingcore.cdc.constant.SpringProfilesConstant.ADVANCED_PRICING;
import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Profile("!" + ADVANCED_PRICING)
public class PriceRepository extends AbstractApiRepository implements PriceStore {

    private final PricesApi pricesApi;

    private Integer defaultTimeOut;

    public PriceRepository(final ObjectMapper objectMapper, final PricesApi pricesApi) {
        super(notNull(objectMapper));
        this.pricesApi = notNull(pricesApi);
    }

    @Override
    public List<ConnectorPrice> priceForConnectors(final List<ConnectorId> connectorIds) {
        if (connectorIds.isEmpty()) {
            return Collections.emptyList();
        }

        final List<ApiPrice> response = getResponseOrPriceError(
                pricesApi.getPrices(connectorIdsAsLong(connectorIds), Instant.now().toEpochMilli()));
        return response.stream().map(this::apiPriceToModel).collect(toList());
    }

    private List<Long> connectorIdsAsLong(List<ConnectorId> connectorIds) {
        return connectorIds.stream().map(connectorId -> connectorId.id).collect(toList());
    }

    private <T, E extends ExternalApiException> T getResponseOrPriceError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), PriceApiException::new);
    }

    private ConnectorPrice apiPriceToModel(final ApiPrice apiPrice) {
        return new ConnectorPrice(new ConnectorId(apiPrice.getConnectorId()), format("%s\u00A0%s/%s", apiPrice.getPricePerUnit(), apiPrice.getCurrency(), apiPrice.getUnit().getValue()));
    }

    @Value("${app.payments-service.default-timeout}")
    public void setDefaultTimeOut(final Integer defaultTimeOut) {
        this.defaultTimeOut = defaultTimeOut;
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeOut;
    }
}
