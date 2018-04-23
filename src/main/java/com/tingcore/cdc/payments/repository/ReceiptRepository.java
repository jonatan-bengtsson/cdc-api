package com.tingcore.cdc.payments.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.payments.cpo.api.ReceiptApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;

@Repository
public class ReceiptRepository extends AbstractApiRepository {

    private Integer defaultTimeOut;
    private final ReceiptApi receiptApi;

    public ReceiptRepository(final ObjectMapper objectMapper,
                             final ReceiptApi receiptApi) {
        super(objectMapper);
        this.receiptApi = receiptApi;
    }

    public String getReceipt(final Long sessionId) {
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
