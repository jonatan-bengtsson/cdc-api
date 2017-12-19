package com.tingcore.cdc.charging.repository;

import com.google.common.collect.Lists;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.ConnectorPrice;
import com.tingcore.payments.cpo.api.PricesApi;
import com.tingcore.payments.cpo.model.ApiPrice;
import org.springframework.stereotype.Repository;
import retrofit2.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class PriceRepository {
    private final PricesApi pricesApi;

    public PriceRepository(final PricesApi pricesApi) {
        this.pricesApi = notNull(pricesApi);
    }

    public List<ConnectorPrice> priceForConnectors(final ConnectorId... connectorIds) {
        return priceForConnectors(Lists.newArrayList(connectorIds));
    }

    public List<ConnectorPrice> priceForConnectors(final List<ConnectorId> connectorIds) {
        try {
            final Response<List<ApiPrice>> response = pricesApi.getPrices(connectorIds.stream().map(connectorId -> connectorId.value).collect(toList()), Instant.now().toEpochMilli()).execute();
            if (!response.isSuccessful()) {
                return emptyList();
            }
            return response.body().stream().map(this::apiPriceToModel).collect(toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private ConnectorPrice apiPriceToModel(final ApiPrice apiPrice) {
        return new ConnectorPrice(new ConnectorId(apiPrice.getConnectorId()), apiPrice.getPrice());
    }
}
