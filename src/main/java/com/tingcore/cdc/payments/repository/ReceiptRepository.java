package com.tingcore.cdc.payments.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.payments.cpo.api.ReceiptsApi;
import com.tingcore.payments.cpo.model.ApiReceipt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;

@Repository
public class ReceiptRepository extends AbstractApiRepository {

    private Integer defaultTimeOut;
    private final ReceiptsApi receiptApi;

    public ReceiptRepository(final ObjectMapper objectMapper,
                             final ReceiptsApi receiptApi) {
        super(objectMapper);
        this.receiptApi = receiptApi;
    }

    public ApiReceipt getReceipt(final Long sessionId) {
        return getResponseOrReceiptError(receiptApi.getUrlForReceiptPdf(sessionId));
    }

    private <T, E extends ExternalApiException> T getResponseOrReceiptError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), ReceiptApiException::new);
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
