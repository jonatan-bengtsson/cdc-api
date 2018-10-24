package com.tingcore.cdc.receipt.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.exception.ReceiptApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.receipt.client.api.v1.PaymentsReceiptRestApi;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import static com.tingcore.cdc.constant.SpringProfilesConstant.PAYMENTS_RECEIPT_V2;
import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;

@Repository
@Profile(PAYMENTS_RECEIPT_V2)
public class ReceiptRepository extends AbstractApiRepository {

    // todo change to autowired bean instead?
    private final PaymentsReceiptRestApi receiptApi;
    private Integer defaultTimeout;

    ReceiptRepository(ObjectMapper objectMapper,
                             PaymentsReceiptRestApi receiptApi) {
        super(objectMapper);
        this.receiptApi = receiptApi;
    }

    public String getReceipt(final Long sessionId){
       ApiResponse<String> fetchReceipt = execute(receiptApi.getReceiptUrlBySessionId(sessionId));
       // Todo create a new exception for this new api?
        return getResponseOrThrowError(fetchReceipt, ReceiptApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeout;
    }
}
