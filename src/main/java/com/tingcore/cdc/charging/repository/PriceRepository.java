package com.tingcore.cdc.charging.repository;

import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.PriceInformation;
import com.tingcore.cdc.exception.NoPriceFoundException;
import com.tingcore.payments.cpo.api.PricesApi;
import com.tingcore.payments.cpo.model.ApiPrice;
import org.springframework.stereotype.Repository;
import retrofit2.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class PriceRepository {
    private final PricesApi pricesApi;

    public PriceRepository(final PricesApi pricesApi) {
        this.pricesApi = notNull(pricesApi);
    }

    public PriceInformation priceForConnector(final ConnectorId connectorId) {
        try {
            final Response<ApiPrice> response = pricesApi.getPrices(connectorId.value, Instant.now().toEpochMilli()).execute();
            if (response.code() == 404) {
                throw new NoPriceFoundException("Connector price not found.");
            }
            return apiPriceToModel(response.body());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private PriceInformation apiPriceToModel(final ApiPrice apiPrice) {
        return new PriceInformation(apiPrice.getFormattedPricePerUnit(), apiPrice.getCurrency());
    }
}
