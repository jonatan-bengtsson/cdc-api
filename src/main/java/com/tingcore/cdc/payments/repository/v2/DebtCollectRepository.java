package com.tingcore.cdc.payments.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.exception.DebtCollectApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.payments.debt.collector.api.v1.DebtCollectorRestApi;
import com.tingcore.payments.debt.collector.request.ApiClearSessionRequest;
import com.tingcore.payments.events.domain.ClearingDebtSourceV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class DebtCollectRepository extends AbstractApiRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DebtCollectRepository.class);

    private Integer defaultTimeOut;
    private final DebtCollectorRestApi debtCollectorRestApi;

    public DebtCollectRepository(final ObjectMapper objectMapper,
                                 @Value("${app.debt-collect.default-timeout}") Integer defaultTimeOut,
                                 final DebtCollectorRestApi debtCollectorRestApi) {
        super(objectMapper);
        this.defaultTimeOut = notNull(defaultTimeOut);
        this.debtCollectorRestApi = notNull(debtCollectorRestApi);
    }

    public Long clearSession(Long sessionId) {
        final ApiClearSessionRequest req = new ApiClearSessionRequest(sessionId, ClearingDebtSourceV1.CLEARING_CDC.name());
        ApiResponse<Long> clearSession = execute(debtCollectorRestApi.clearDebtForSession(req));
        return getResponseOrThrowError(clearSession, DebtCollectApiException::new);

    }

    @Override
    public Integer getTimeout() {
        return defaultTimeOut;
    }
}
