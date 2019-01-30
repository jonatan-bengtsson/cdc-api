package com.tingcore.cdc.payments.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.exception.ReceiptApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.receipt.client.api.v1.PaymentsReceiptRestApi;
import org.springframework.stereotype.Repository;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;

@Repository
public class ReceiptRepository extends AbstractApiRepository {

    private final PaymentsReceiptRestApi receiptApi;
    private static final Integer defaultTimeout = 20;

    ReceiptRepository(ObjectMapper objectMapper,
                      PaymentsReceiptRestApi receiptApi) {
        super(objectMapper);
        this.receiptApi = receiptApi;
    }

    public String getReceipt(final Long sessionId) {
        ApiResponse<String> fetchReceipt = execute(receiptApi.getReceiptUrlBySessionId(sessionId));
        return getResponseOrThrowError(fetchReceipt, ReceiptApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeout;
    }
}
